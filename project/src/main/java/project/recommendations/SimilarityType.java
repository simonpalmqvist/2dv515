package project.recommendations;

public class SimilarityType {

    private static final SimilarityType instance = new SimilarityType();
    private boolean usePearson = false;

    private SimilarityType() {}

    public static SimilarityType getInstance () {
        return instance;
    }

    public boolean isUsePearson() {
        return usePearson;
    }

    public void usePearson() {
        this.usePearson = true;
    }

    public void useEuclidean() {
        this.usePearson = false;
    }
}
