package project.movies;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class Movie {
    private final String name;
    final private Map<String, Double> ratings = new HashMap<>();

    public Movie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void addRating(String userName, double rating) {
        ratings.put(userName, rating);
    }

    @JsonIgnore
    public Map<String, Double> getRatings() {
        return ratings;
    }
}
