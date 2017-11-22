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

        //Add k number of centroids and add random item to the centroid
        for(int i = 0; i < k; i++) {
            centroids.add(new Centroid<>(items.get(random.nextInt(items.size()))));
        }


        // Re-add items to centroids and recalculate center until a centroid gets same items in two iterations
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

        // Return list of item sets representing each cluster
        return centroids.stream().map(Centroid::getItems).collect(Collectors.toList());
    }

    private static <T extends PearsonItem> void addItemsToCentroids(List<Centroid<T>> centroids, List<T> items) {
        // Reset so no items are added to the centroids
        centroids.forEach(Centroid::resetItems);

        // For every item we check which centroid is the best match for the item and add it to that centroid
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
