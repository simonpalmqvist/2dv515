package project.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.movies.Movie;
import project.users.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {

    @Autowired
    private RecommendationsRepository repository;

    public void storeUserBasedRecommendedMovies(Collection<User> users, Collection<Movie> movies) {
        for(User userA : users) {

            Set<Recommendation> recommendations = movies
                    .stream()
                    .filter(m -> m.getRatings().size() > 0 && !userA.getRatings().containsKey(m.getId()))
                    .map(Recommendation::new)
                    .collect(Collectors.toSet());

            for(User userB : users) {
                Map<Integer, Double> bRatings = userB.getRatings();

                double euclideanSimilarityScore = Euclidean.calculateScore(userA.getRatings(), bRatings);

                if(euclideanSimilarityScore == 0) continue; // Skip checking for recommendations if user are not similar

                double pearsonSimilarityScore = Pearson.calculateScore(userA.getRatings(), bRatings);

                for(Recommendation recommendation : recommendations) {
                    int movieId = recommendation.getMovie().getId();

                    if(bRatings.containsKey(movieId)) {
                        recommendation.euclideanTotalScore += euclideanSimilarityScore * bRatings.get(movieId);
                        recommendation.euclideanSimilarityScore += euclideanSimilarityScore;
                        recommendation.pearsonTotalScore += pearsonSimilarityScore * bRatings.get(movieId);
                        recommendation.pearsonSimilarityScore += pearsonSimilarityScore;
                    }
                }
            }

            repository.addUserBasedRecommendation(userA.getId(), recommendations);
        }
    }

    public void storeItemBasedRecommendedMovies(Collection<User> users, Collection<Movie> movies) {
        Map<Integer, Map<Movie, Double>> euclideanSimilarityScores = new HashMap<>();
        Map<Integer, Map<Movie, Double>> pearsonSimilarityScores = new HashMap<>();

        Collection<Movie> filteredMovies = movies
                .stream()
                .filter(m -> m.getRatings().size() > 0).collect(Collectors.toSet());

        for(Movie movieA : filteredMovies) {
            Map<Movie, Double> euclideanMovieScores = new HashMap<>();
            Map<Movie, Double> pearsonMovieScores = new HashMap<>();

            euclideanSimilarityScores.put(movieA.getId(), euclideanMovieScores);
            pearsonSimilarityScores.put(movieA.getId(), pearsonMovieScores);

            for (Movie movieB : filteredMovies) {
                if (movieA != movieB) {
                    Map<Integer, Double> bRatings = movieB.getRatings();

                    double euclideanSimilarityScore = Euclidean.calculateScore(movieA.getRatings(), bRatings);

                    if(euclideanSimilarityScore == 0) continue; // Skip storing movies that have no similatiy in ratings

                    euclideanMovieScores.put(movieB, euclideanSimilarityScore);
                    pearsonMovieScores.put(movieB, Pearson.calculateScore(movieA.getRatings(), bRatings));
                }

            }
        }

        for(User userA : users) {

            Set<Recommendation> recommendations = filteredMovies
                    .stream()
                    .filter(m -> !userA.getRatings().containsKey(m.getId()))
                    .map(Recommendation::new)
                    .collect(Collectors.toSet());

            for(Map.Entry<Integer, Double> moviesRated : userA.getRatings().entrySet()) {

                for(Recommendation recommendation : recommendations) {

                    double euclideanSimilarityScore = euclideanSimilarityScores
                            .get(moviesRated.getKey())
                            .get(recommendation.getMovie());
                    double pearsonSimilarityScore = pearsonSimilarityScores
                            .get(moviesRated.getKey())
                            .get(recommendation.getMovie());


                    recommendation.euclideanTotalScore += euclideanSimilarityScore * moviesRated.getValue();
                    recommendation.euclideanSimilarityScore += euclideanSimilarityScore;
                    recommendation.pearsonTotalScore += pearsonSimilarityScore * moviesRated.getValue();
                    recommendation.pearsonSimilarityScore += pearsonSimilarityScore;
                }
            }

            repository.addItemBasedRecommendation(userA.getId(), recommendations);
        }

    }

    List<Recommendation> findUserBasedRecommendation(int userId, boolean pearson) {
        if(pearson) SimilarityType.getInstance().usePearson();
        else SimilarityType.getInstance().useEuclidean();

        return repository.findUserBasedRecommendation(userId)
                .stream()
                .sorted()
                .limit(100)
                .collect(Collectors.toList());
    }

    List<Recommendation> findItemBasedRecommendation(int userId, boolean pearson) {
        if(pearson) SimilarityType.getInstance().usePearson();
        else SimilarityType.getInstance().useEuclidean();

        return repository.findItemBasedRecommendation(userId)
                .stream()
                .sorted()
                .limit(100)
                .collect(Collectors.toList());
    }
}
