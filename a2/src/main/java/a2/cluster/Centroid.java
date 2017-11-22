package a2.cluster;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Centroid <T extends PearsonItem>  {

    private Set<T> previousItems;
    private Set<T> currentItems;
    private Map<Object, Double> values;

    Centroid(T item) {
        values = new HashMap<>(item.getValues());
        currentItems = new HashSet<>();
    }

    void resetItems() {
        previousItems = currentItems; // Store last iteration when reseting centroid to be able to check if next iteration matches the last one
        currentItems = new HashSet<>();
    }

    void addItem(T item) {
        currentItems.add(item);
    }

    boolean matchesPreviousItems() {
        return previousItems.containsAll(currentItems);
    }

    void reCalculateCenter() {
        values.keySet().forEach(id -> {
            double avg = 0;
            for(PearsonItem item : currentItems) avg += item.getValues().get(id);

            values.put(id, avg / (double) currentItems.size());
        });
    }

    Map<?, Double> getValues() {
        return values;
    }

    public Set<T> getItems() {
        return new HashSet<>(currentItems);
    }
}
