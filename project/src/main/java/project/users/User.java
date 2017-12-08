package project.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class User {

    private final String name;
    final private Map<String, Double> ratings = new HashMap<>();

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRating(String movieName, double rating) {
        ratings.put(movieName, rating);
    }

    @JsonIgnore
    public Map<String, Double> getRatings() {
        return ratings;
    }
}
