package project.recommendations;

import project.movies.Movie;

public class Recommendation implements Comparable<Recommendation> {

    private final Movie movie;
    public double totalScore = 0.0;
    public double similarityScore = 0.0;

    public Recommendation(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getWeightedScore() {
        return totalScore / similarityScore;
    }

    @Override
    public int compareTo(Recommendation o) {
        return Double.compare(o.getWeightedScore(), getWeightedScore());
    }
}
