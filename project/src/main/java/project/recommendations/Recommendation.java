package project.recommendations;

import project.movies.Movie;

public class Recommendation implements Comparable<Recommendation> {

    private final Movie movie;
    private final SimilarityType similarityType = SimilarityType.getInstance();
    public double euclideanTotalScore = 0.0;
    public double euclideanSimilarityScore = 0.0;
    public double pearsonTotalScore = 0.0;
    public double pearsonSimilarityScore = 0.0;

    public Recommendation(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getWeightedScore() {
        return similarityType.isUsePearson() ?
                pearsonTotalScore / pearsonSimilarityScore :
                euclideanTotalScore / euclideanSimilarityScore;
    }

    @Override
    public int compareTo(Recommendation o) {
        return Double.compare(o.getWeightedScore(), getWeightedScore());
    }
}
