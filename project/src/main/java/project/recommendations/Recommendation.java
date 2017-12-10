package project.recommendations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import project.movies.Movie;

public class Recommendation implements Comparable<Recommendation> {

    private final Movie movie;

    @JsonIgnore public double totalScore = 0.0;
    @JsonIgnore public double similarityScore = 0.0;

    public Recommendation(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getWeightedScore() {
        return similarityScore == 0 ? 0 : totalScore / similarityScore;
    }

    @Override
    public int compareTo(Recommendation o) {
        return Double.compare(o.getWeightedScore(), getWeightedScore());
    }
}
