package project.ratings;


public class Rating {

    private final String movieName;
    private final double rating;

    public Rating(String movieName, double rating) {
        this.movieName = movieName;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public String getMovieName() {
        return movieName;
    }
}
