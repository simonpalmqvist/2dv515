package project.recommendations;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class RecommendationsRepository {

    private final Map<Integer, Map<Integer, SimilarityScore>> similarityScores = new HashMap<>();

    void addSimilarityScores(int id, Map<Integer, SimilarityScore> scores) {
        similarityScores.put(id, scores);
    }

    Map<Integer, SimilarityScore> getSimilarityScores(int id) {
        return similarityScores.get(id);
    }
}
