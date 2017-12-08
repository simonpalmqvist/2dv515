package project.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.movies.Movie;
import project.users.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {

    @Autowired
    private RecommendationsRepository repository;

    public void storeRecommendedMovies(Collection<User> users, Collection<Movie> movies) {
        storeUserBasedRecommendedMovies(users, movies);
        storeItemBasedRecommendedMovies(users, movies);
    }

    private void storeUserBasedRecommendedMovies(Collection<User> users, Collection<Movie> movies) {
        for(User userA : users) {

            Set<Recommendation> recommendations = movies
                    .stream()
                    .filter(m -> !userA.getRatings().containsKey(m.getName()))
                    .map(Recommendation::new)
                    .collect(Collectors.toSet());

            for(User userB : users) {
                Map<String, Double> bRatings = userB.getRatings();

                double euclideanSimilarityScore = Euclidean.calculateScore(userA.getRatings(), bRatings);
                double pearsonSimilarityScore = Pearson.calculateScore(userA.getRatings(), bRatings);

                for(Recommendation recommendation : recommendations) {
                    String movieName = recommendation.getMovie().getName();

                    if(bRatings.containsKey(movieName)) {
                        recommendation.euclideanTotalScore += euclideanSimilarityScore * bRatings.get(movieName);
                        recommendation.euclideanSimilarityScore += euclideanSimilarityScore;
                        recommendation.pearsonTotalScore += pearsonSimilarityScore * bRatings.get(movieName);
                        recommendation.pearsonSimilarityScore += pearsonSimilarityScore;
                    }
                }
            }

            repository.addUserBasedRecommendation(userA.getName(), recommendations);

            printRecommendations(userA.getName(), recommendations);
        }
    }

    private void storeItemBasedRecommendedMovies(Collection<User> users, Collection<Movie> movies) {
        Map<String, Map<Movie, Double>> euclideanSimilarityScores = new HashMap<>();
        Map<String, Map<Movie, Double>> pearsonSimilarityScores = new HashMap<>();

        for(Movie movieA : movies) {
            Map<Movie, Double> euclideanMovieScores = new HashMap<>();
            Map<Movie, Double> pearsonMovieScores = new HashMap<>();

            euclideanSimilarityScores.put(movieA.getName(), euclideanMovieScores);
            pearsonSimilarityScores.put(movieA.getName(), pearsonMovieScores);

            for (Movie movieB : movies) {
                if (movieA != movieB) {
                    Map<String, Double> bRatings = movieB.getRatings();

                    euclideanMovieScores.put(movieB, Euclidean.calculateScore(movieA.getRatings(), bRatings));
                    pearsonMovieScores.put(movieB, Pearson.calculateScore(movieA.getRatings(), bRatings));
                }

            }
        }

        for(User userA : users) {

            Set<Recommendation> recommendations = movies
                    .stream()
                    .filter(m -> !userA.getRatings().containsKey(m.getName()))
                    .map(Recommendation::new)
                    .collect(Collectors.toSet());

            for(Map.Entry<String, Double> moviesRated : userA.getRatings().entrySet()) {

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

            repository.addItemBasedRecommendation(userA.getName(), recommendations);

            printRecommendations(userA.getName(), recommendations);
        }

    }


    private void printRecommendations(String name, Set<Recommendation> recommendations) {
        SimilarityType.getInstance().useEuclidean();
        System.out.println(name);
        System.out.println("EUCLIDEAN");
        recommendations
                .stream()
                .sorted()
                .forEach(r -> System.out.println(r.getMovie().getName() + " score " + r.getWeightedScore() ));

        SimilarityType.getInstance().usePearson();

        System.out.println("PEARSON");
        recommendations
                .stream()
                .sorted()
                .forEach(r -> System.out.println(r.getMovie().getName() + " score " + r.getWeightedScore() ));
        System.out.println();
        System.out.println();
    }
}
