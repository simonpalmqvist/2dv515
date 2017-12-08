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

                double similarityScore = Euclidean.calculateScore(userA.getRatings(), bRatings);

                for(Recommendation recommendation : recommendations) {
                    String movieName = recommendation.getMovie().getName();

                    if(bRatings.containsKey(movieName)) {
                        recommendation.totalScore += similarityScore * bRatings.get(movieName);
                        recommendation.similarityScore += similarityScore;
                    }
                }
            }

            repository.addRecommendation(userA.getName(), recommendations
                    .stream()
                    .sorted()
                    .collect(Collectors.toSet())
            );
            recommendations
                    .stream()
                    .sorted()
                    .forEach(r -> System.out.println(r.getMovie().getName() + " score " + r.getWeightedScore() ));
            System.out.println();
            System.out.println();
        }
    }
}
