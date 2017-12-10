package project.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.movies.Movie;
import project.movies.MoviesRepository;
import project.users.User;
import project.users.UsersRepository;
import project.users.UsersService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {

    private Map<Integer, Map<Integer, SimilarityScore>> similarityScores = new HashMap<>();

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RecommendationsRepository repository;

    public void storeSimilarityBetweenMovies() {

        Collection<Movie> filteredMovies = moviesRepository.getMovies()
                .stream()
                .filter(m -> m.getRatings().size() > 0)
                .collect(Collectors.toSet());

        for(Movie movieA : filteredMovies) {
            Map<Integer, SimilarityScore> movieScores = new HashMap<>();

            similarityScores.put(movieA.getId(), movieScores);

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

        Set<Recommendation> recommendations = getMoviesNotSeenByUser(user);

        for(User userB : usersService.getUsers()) {
            Map<Integer, Double> bRatings = userB.getRatings();

            double similarityScore = pearson ?
                    Pearson.calculateScore(user.getRatings(), bRatings) :
                    Euclidean.calculateScore(user.getRatings(), bRatings);

            if (similarityScore == 0) continue; // Skip checking for recommendations if user are not similar

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

        Set<Recommendation> recommendations = getMoviesNotSeenByUser(user);

        for(Map.Entry<Integer, Double> ratedMovie : user.getRatings().entrySet()) {

            for(Recommendation recommendation : recommendations) {

                SimilarityScore similarityScore = similarityScores
                        .get(ratedMovie.getKey())
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

    private Set<Recommendation> getMoviesNotSeenByUser(User user) {
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
