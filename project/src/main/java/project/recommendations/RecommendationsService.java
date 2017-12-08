package project.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.movies.Movie;
import project.users.User;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {

    @Autowired
    private RecommendationsRepository repository;

    public void storeRecommendedMovies(Collection<User> users,  Collection<Movie> movies) {
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

            repository.addRecommendation(userA.getName(), recommendations
                    .stream()
                    .sorted()
                    .collect(Collectors.toSet())
            );

            SimilarityType.getInstance().useEuclidean();
            System.out.println(userA.getName());
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
}
