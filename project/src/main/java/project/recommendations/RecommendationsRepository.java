package project.recommendations;

import org.springframework.stereotype.Repository;
import project.users.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class RecommendationsRepository {

    final private Map<String, Set<Recommendation>> recommendations = new HashMap<>();

    void addRecommendation(String user, Set<Recommendation> recommendation) {
        recommendations.put(user, recommendation);
    }

    Set<Recommendation> findRecommendation(String user) {
        return recommendations.get(user);
    }

}
