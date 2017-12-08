package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.movies.Movie;
import project.movies.MoviesRepository;
import project.ratings.Rating;
import project.users.User;
import project.users.UsersRepository;

@Component
public class Init implements ApplicationRunner {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    public void run(ApplicationArguments args) {
        Movie ladyInTheWater = moviesRepository.addMovie("Lady in the Water");
        Movie snakesOnAPlane = moviesRepository.addMovie("Snakes on a Plane");
        Movie justMyLuck = moviesRepository.addMovie("Just My Luck");
        Movie supermanReturns = moviesRepository.addMovie("Superman Returns");
        Movie youMeAndDupree = moviesRepository.addMovie("You, Me and Dupree ");
        Movie theNightListener = moviesRepository.addMovie("The Night Listener");

        User lisa = usersRepository.addUser("Lisa");
        User gene = usersRepository.addUser("Gene");
        User michael = usersRepository.addUser("Michael");
        User claudia = usersRepository.addUser("Claudia");
        User mick = usersRepository.addUser("Mick");
        User jack = usersRepository.addUser("Jack");
        User toby = usersRepository.addUser("Toby");

        createRating(lisa, ladyInTheWater, 2.5);
        createRating(gene, ladyInTheWater, 3.0);
        createRating(michael, ladyInTheWater, 2.5);
        createRating(mick, ladyInTheWater, 3.0);
        createRating(jack, ladyInTheWater, 3.0);

        createRating(lisa, snakesOnAPlane, 3.5);
        createRating(gene, snakesOnAPlane, 3.5);
        createRating(michael, snakesOnAPlane, 3.0);
        createRating(claudia, snakesOnAPlane, 3.5);
        createRating(mick, snakesOnAPlane, 4.0);
        createRating(jack, snakesOnAPlane, 4.0);
        createRating(toby, snakesOnAPlane, 4.5);

        createRating(lisa, justMyLuck, 3.0);
        createRating(gene, justMyLuck, 1.5);
        createRating(claudia, justMyLuck, 3.0);
        createRating(mick, justMyLuck, 2.0);

        createRating(lisa, supermanReturns, 3.5);
        createRating(gene, supermanReturns, 5.0);
        createRating(michael, supermanReturns, 3.5);
        createRating(claudia, supermanReturns, 4.0);
        createRating(mick, supermanReturns, 3.0);
        createRating(jack, supermanReturns, 5.0);
        createRating(toby, supermanReturns, 4.0);

        createRating(lisa, youMeAndDupree, 2.5);
        createRating(gene, youMeAndDupree, 3.5);
        createRating(claudia, youMeAndDupree, 2.5);
        createRating(mick, youMeAndDupree, 2.0);
        createRating(jack, youMeAndDupree, 3.5);
        createRating(toby, youMeAndDupree, 1.0);

        createRating(lisa, theNightListener, 3.0);
        createRating(gene, theNightListener, 3.0);
        createRating(michael, theNightListener, 4.0);
        createRating(claudia, theNightListener, 4.5);
        createRating(mick, theNightListener, 3.0);
        createRating(jack, theNightListener, 3.0);
    }

    private void createRating(User user, Movie movie, double ratingValue) {
        Rating rating = new Rating(movie.getName(), ratingValue);
        user.addRating(rating);
    }
}


/*
DATA SET EXAMPLE

Movie                Lisa Gene Michael Claudia Mick Jack Toby
Lady in the Water    2.5  3.0  2.5             3.0  3.0
Snakes on a Plane    3.5  3.5  3.0     3.5     4.0  4.0  4.5
Just My Luck         3.0  1.5          3.0     2.0
Superman Returns     3.5  5.0  3.5     4.0     3.0  5.0  4.0
You, Me and Dupree   2.5  3.5          2.5     2.0  3.5  1.0
The Night Listener   3.0  3.0  4.0     4.5     3.0  3.0

 */