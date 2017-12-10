package project.recommendations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import project.movies.Movie;

public class Recommendation implements Comparable<Recommendation> {

    private final Movie movie;
    private final SimilarityType similarityType = SimilarityType.getInstance();

    @JsonIgnore public double euclideanTotalScore = 0.0;
    @JsonIgnore public double euclideanSimilarityScore = 0.0;
    @JsonIgnore public double pearsonTotalScore = 0.0;
    @JsonIgnore public double pearsonSimilarityScore = 0.0;

    public Recommendation(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getWeightedScore() {
        if(similarityType.isUsePearson()) {
            return pearsonSimilarityScore == 0 ? 0 : pearsonTotalScore / pearsonSimilarityScore;
        } else {
            return euclideanSimilarityScore == 0 ? 0 : euclideanTotalScore / euclideanSimilarityScore;
        }
    }

    @Override
    public int compareTo(Recommendation o) {
        return Double.compare(o.getWeightedScore(), getWeightedScore());
    }
}
