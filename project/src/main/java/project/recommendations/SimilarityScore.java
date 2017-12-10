package project.recommendations;

public class SimilarityScore {

    private final double euclideanScore;
    private final double pearsonScore;

    public SimilarityScore(double euclideanScore, double pearsonScore) {
        this.euclideanScore = euclideanScore;
        this.pearsonScore = pearsonScore;
    }

    public double getEuclideanScore() {
        return euclideanScore;
    }

    public double getPearsonScore() {
        return pearsonScore;
    }
}
