package project.movies;

import org.springframework.stereotype.Repository;
import project.users.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MoviesRepository {

    final private Map<String, Movie> movies = new HashMap<>();

    public Movie addMovie(String name) {
        Movie movie = new Movie(name);

        movies.put(name, movie);

        return movie;
    }

    public Collection<Movie> getMovies() {
        return movies.values();
    }
}
