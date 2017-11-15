package a2.cluster;


import java.util.*;
import java.util.stream.Collectors;

public class KmeansCluster {

    public static <T extends PearsonItem> List<Set<T>> createCluster(Collection<T> itemCollection, int k) {
        ArrayList<Centroid<T>> centroids = new ArrayList<>();
        ArrayList<T> items = new ArrayList<>(itemCollection);
        Random random = new Random();
        boolean done = false;
        int iterations = 0;

        for(int i = 0; i < k; i++) {
            centroids.add(new Centroid<>(items.get(random.nextInt(items.size()))));
        }


        while(!done) {
            addItemsToCentroids(centroids, items);


            done = true;
            for(Centroid centroid : centroids) {
                centroid.reCalculateCenter();

                if(!centroid.matchesPreviousItems()) done = false;
            }

            iterations++;
        }

        System.out.println("Iterations: " + iterations + ", Centroids: " + centroids.size() + "\n\n");

        return centroids.stream().map(Centroid::getItems).collect(Collectors.toList());
    }

    private static <T extends PearsonItem> void addItemsToCentroids(List<Centroid<T>> centroids, List<T> items) {
        centroids.forEach(Centroid::resetItems);

        items.forEach(item -> {
            double bestResult = 0;
            Centroid<T> bestMatch = centroids.get(0);

            for(Centroid<T> centroid : centroids) {
                double pearsonScore = Pearson.calculateScore(item.getValues(), centroid.getValues());

                if(pearsonScore > bestResult) {
                    bestResult = pearsonScore;
                    bestMatch = centroid;
                }
            }

            bestMatch.addItem(item);
        });
    }

}
