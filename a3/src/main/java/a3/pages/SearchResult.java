package a3.pages;

public class SearchResult implements Comparable<SearchResult> {

    private final double score;
    private final Page page;

    SearchResult(Page page, double score) {
        this.page = page;
        this.score = score;
    }

    @Override
    public int compareTo(SearchResult compareTo) {
        return Double.compare(compareTo.getScore(), score);
    }

    public double getScore() {
        return score;
    }

    public Page getPage() {
        return page;
    }
}
