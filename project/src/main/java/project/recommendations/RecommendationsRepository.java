package project.recommendations;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class RecommendationsRepository {

    final private Map<String, Set<Recommendation>> userBasedRecommendations = new HashMap<>();
    final private Map<String, Set<Recommendation>> itemBasedRecommendations = new HashMap<>();

    void addUserBasedRecommendation(String user, Set<Recommendation> recommendation) {
        userBasedRecommendations.put(user, recommendation);
    }

    void addItemBasedRecommendation(String user, Set<Recommendation> recommendation) {
        itemBasedRecommendations.put(user, recommendation);
    }

    Set<Recommendation> findUserBasedRecommendation(String user) {
        return userBasedRecommendations.get(user);
    }

    Set<Recommendation> findItemBasedRecommendation(String user) {
        return itemBasedRecommendations.get(user);
    }
}
