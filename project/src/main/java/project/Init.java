package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import project.movies.Movie;
import project.movies.MoviesRepository;
import project.recommendations.RecommendationsService;
import project.users.User;
import project.users.UsersService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

@Component
public class Init implements ApplicationRunner {

    @Autowired
    private UsersService usersService;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private RecommendationsService recommendationsService;
    private long startTime;

    public void run(ApplicationArguments args) throws IOException {
        File movieFile = ResourceUtils.getFile("classpath:data/movies.csv");
        File ratingsFile = ResourceUtils.getFile("classpath:data/ratings.csv");



        startTimer("ADDING MOVIES");
        Files
                .lines(movieFile.toPath())
                .skip(1)
                .map(l -> l.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
                .forEach(data -> {
                    moviesRepository.addMovie(Integer.parseInt(data[0]), data[1].replace("\"", ""));
                });
        endTimer();

        startTimer("ADDING USERS & RATINGS");
        Files
                .lines(ratingsFile.toPath())
                .skip(1)
                .map(l -> l.split(","))
                .forEach(data -> {
                    User user = usersService.addUser(Integer.parseInt(data[0]), "User " + data[0]);
                    Movie movie = moviesRepository.getMovie(Integer.parseInt(data[1]));
                    createRating(user, movie, Double.parseDouble(data[2]));
                });
        endTimer();


        startTimer("STORE ITEM SIMILARITY SCORES");
        recommendationsService.storeSimilarityBetweenMovies();
        endTimer();
    }

    private void createRating(User user, Movie movie, double rating) {
        user.addRating(movie.getId(), rating);
        movie.addRating(user.getId(), rating);
    }

    private void startTimer(String task) {
        System.out.println("-- " + task + " --");
        startTime = System.currentTimeMillis();
    }

    private void endTimer() {
        System.out.println("-- DONE IN " + (System.currentTimeMillis()-startTime)/1000F +  "s --");
    }
}
