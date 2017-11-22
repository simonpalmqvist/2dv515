package a2.cluster;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HierarchicalCluster<T extends PearsonItem> {

    private HierarchicalCluster<T> parent = null;
    private HierarchicalCluster<T> left = null;
    private HierarchicalCluster<T> right = null;
    private T item = null;
    private Map<Object, Double> avgValues = null;
    double distance;

    public HierarchicalCluster() {
        avgValues = new HashMap<>();
    }

    public HierarchicalCluster(T i) {
        item = i;
    }



    HierarchicalCluster<T> merge(HierarchicalCluster<T> cluster, double distance) {
        HierarchicalCluster<T> mergedCluster = new HierarchicalCluster<>();

        // Create new cluster for this and another cluster and calculate average score for cluster
        mergedCluster.setLeft(this);
        mergedCluster.setRight(cluster);
        this.setParent(mergedCluster);
        cluster.setParent(mergedCluster);

        for( Object key : getValues().keySet()) {
            double valueA = getValues().get(key);
            double valueB = cluster.getValues().get(key);

            double valueAvg = (valueA + valueB) / 2.0;

            mergedCluster.addAvgValue(key, valueAvg);
        }

        mergedCluster.setDistance(distance);

        return mergedCluster;
    }

    void addAvgValue(Object key, Double value) {
        if(avgValues == null) return;

        avgValues.put(key, value);
    }

    @JsonIgnore
    Map<?, Double> getValues() {
        return item == null ? avgValues : item.getValues();
    }

    public T getItem() {
        return item;
    }

    public HierarchicalCluster<T> getLeft() {
        return left;
    }

    public void setLeft(HierarchicalCluster<T> left) {
        this.left = left;
    }

    public HierarchicalCluster<T> getRight() {
        return right;
    }

    public void setRight(HierarchicalCluster<T> right) {
        this.right = right;
    }

    @JsonIgnore
    public HierarchicalCluster<T> getParent() {
        return parent;
    }

    public void setParent(HierarchicalCluster<T> parent) {
        this.parent = parent;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public static <T extends PearsonItem> HierarchicalCluster<T> createClusters(Collection<T> items) {
        ArrayList<HierarchicalCluster<T>> clusters = new ArrayList<>();

        // Create clusters from pearson items
        for(T item : items) clusters.add(new HierarchicalCluster<>(item));


        // as long as there are more root clusters than one, iterate and merge them
        while(clusters.size() > 1) iterate(clusters);

        // return root cluster
        return clusters.get(0);
    }

    private static <T extends PearsonItem> void iterate(ArrayList<HierarchicalCluster<T>> clusters) {
        double closestDistance = Double.MAX_VALUE;
        HierarchicalCluster<T> bestA = null;
        HierarchicalCluster<T> bestB = null;

        // find best match
        for(int i = 0; i < clusters.size(); i++) {
            for(int j = i + 1; j < clusters.size(); j++) {
                HierarchicalCluster<T> currentA = clusters.get(i);
                HierarchicalCluster<T> currentB = clusters.get(j);

                double distance = 1.0 - Pearson.calculateScore(currentA.getValues(), currentB.getValues());

                if(distance < closestDistance) {
                    closestDistance = distance;
                    bestA = currentA;
                    bestB = currentB;
                }
            }
        }

        // merge best match and add new cluster and remove best match clusters from clusters
        HierarchicalCluster<T> mergedCluster = bestA.merge(bestB, closestDistance);

        clusters.add(mergedCluster);

        clusters.remove(bestA);
        clusters.remove(bestB);
    }



}
