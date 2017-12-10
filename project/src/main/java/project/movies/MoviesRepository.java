package project.movies;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MoviesRepository {

    final private Map<Integer, Movie> movies = new HashMap<>();

    public Movie addMovie(int id, String name) {
        Movie movie = new Movie(id, name);

        movies.put(id, movie);

        return movie;
    }

    public Collection<Movie> getMovies() {
        return movies.values();
    }
}
