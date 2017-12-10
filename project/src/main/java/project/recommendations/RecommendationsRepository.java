package project.recommendations;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class RecommendationsRepository {

    final private Map<Integer, Set<Recommendation>> userBasedRecommendations = new HashMap<>();
    final private Map<Integer, Set<Recommendation>> itemBasedRecommendations = new HashMap<>();

    void addUserBasedRecommendation(int userId, Set<Recommendation> recommendation) {
        userBasedRecommendations.put(userId, recommendation);
    }

    void addItemBasedRecommendation(int userId, Set<Recommendation> recommendation) {
        itemBasedRecommendations.put(userId, recommendation);
    }

    Set<Recommendation> findUserBasedRecommendation(int userId) {
        return userBasedRecommendations.get(userId);
    }

    Set<Recommendation> findItemBasedRecommendation(int userId) {
        return itemBasedRecommendations.get(userId);
    }
}
