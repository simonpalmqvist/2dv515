package a2.cluster;

import java.util.Map;
import java.util.HashMap;

public class TestUser {

    private final String name;
    private final Map<String, Double> ratings = new HashMap<>();

    public TestUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void addRating(String movie, Double rating) {
        ratings.put(movie, rating);
    }
}
