package project.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.movies.Movie;
import project.movies.MoviesRepository;
import project.users.User;
import project.users.UsersService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RecommendationsRepository repository;

    public void storeSimilarityBetweenMovies() {

        // Filter away all movies without any ratings
        Collection<Movie> filteredMovies = moviesRepository.getMovies()
                .stream()
                .filter(m -> m.getRatings().size() > 0)
                .collect(Collectors.toSet());

        for(Movie movieA : filteredMovies) {

            // create new score map and store it.
            Map<Integer, SimilarityScore> movieScores = new HashMap<>();
            repository.addSimilarityScores(movieA.getId(), movieScores);

            // calculate scores for all other movies
            for (Movie movieB : filteredMovies) {
                if (movieA != movieB) {
                    Map<Integer, Double> bRatings = movieB.getRatings();

                    double euclideanScore = Euclidean.calculateScore(movieA.getRatings(), bRatings);

                    if(euclideanScore == 0) continue; // Skip storing movies that have no similarity in ratings

                    double pearsonScore = Pearson.calculateScore(movieA.getRatings(), bRatings);

                    movieScores.put(movieB.getId(), new SimilarityScore(euclideanScore, pearsonScore));
                }

            }
        }
    }

    List<Recommendation> findUserBasedRecommendation(int userId, boolean pearson) {
        User user = usersService.getUser(userId);

        Set<Recommendation> recommendations = getMoviesNotRatedByUser(user);

        for(User userB : usersService.getUsers()) {
            Map<Integer, Double> bRatings = userB.getRatings();

            // get score depending on setting
            double similarityScore = pearson ?
                    Pearson.calculateScore(user.getRatings(), bRatings) :
                    Euclidean.calculateScore(user.getRatings(), bRatings);

            if (similarityScore == 0) continue; // Skip checking for recommendations if user are not similar

            // go through movies not rated by user and add a recommendation score.
            for (Recommendation recommendation : recommendations) {
                int movieId = recommendation.getMovie().getId();

                if (bRatings.containsKey(movieId)) {
                    recommendation.totalScore += similarityScore * bRatings.get(movieId);
                    recommendation.similarityScore += similarityScore;
                }
            }
        }

        return getTop100(recommendations);
    }

    List<Recommendation> findItemBasedRecommendation(int userId, boolean pearson) {
        User user = usersService.getUser(userId);

        Set<Recommendation> recommendations = getMoviesNotRatedByUser(user);


        // For each of the users rated movies we go thorugh and get a score for each movie.
        for(Map.Entry<Integer, Double> ratedMovie : user.getRatings().entrySet()) {

            for(Recommendation recommendation : recommendations) {

                // Find similarity scores for the two movies
                SimilarityScore similarityScore = repository
                        .getSimilarityScores(ratedMovie.getKey())
                        .get(recommendation.getMovie().getId());

                if(similarityScore != null) {
                    double score = pearson ? similarityScore.getPearsonScore() : similarityScore.getEuclideanScore();

                    recommendation.totalScore += score * ratedMovie.getValue();
                    recommendation.similarityScore += score;
                }
            }
        }

        return getTop100(recommendations);
    }

    private Set<Recommendation> getMoviesNotRatedByUser(User user) {
        return moviesRepository.getMovies()
                .stream()
                .filter(m -> m.getRatings().size() > 0 && !user.getRatings().containsKey(m.getId()))
                .map(Recommendation::new)
                .collect(Collectors.toSet());
    }

    private List<Recommendation> getTop100(Set<Recommendation> recommendations) {
        return recommendations
                .stream()
                .sorted()
                .limit(100)
                .collect(Collectors.toList());
    }
}
