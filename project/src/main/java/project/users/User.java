package project.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import project.ratings.Rating;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class User {

    private final String name;
    final private Map<String, Rating> ratings = new HashMap<>();

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRating(Rating rating) {
        ratings.put(rating.getMovieName(), rating);
    }

    public Collection<Rating> getRatings() {
        return ratings.values();
    }
}
