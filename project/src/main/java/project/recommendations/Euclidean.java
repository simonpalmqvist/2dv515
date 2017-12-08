package project.recommendations;


import java.util.Map;

public class Euclidean {

    public static double calculateScore(Map<String, Double> a, Map<String, Double> b) {
        double similarity = 0.0;
        int similarItems = 0;

        for(Map.Entry<String, Double> item : a.entrySet()) {
            if(b.containsKey(item.getKey())) {
                similarity += Math.pow(item.getValue() - b.get(item.getKey()), 2);
                similarItems++;
            }
        }

        return similarItems == 0 ? 0 : 1.0 / (1.0  + similarity);
    }
}
