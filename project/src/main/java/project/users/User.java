package project.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class User {

    private final int id;
    private final String name;
    final private Map<Integer, Double> ratings = new HashMap<>();

    User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addRating(int movieId, double rating) {
        ratings.put(movieId, rating);
    }

    @JsonIgnore
    public Map<Integer, Double> getRatings() {
        return ratings;
    }

}
