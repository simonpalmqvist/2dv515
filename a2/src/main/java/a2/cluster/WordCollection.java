package a2.cluster;

import a2.cluster.PearsonItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCollection implements PearsonItem {
    private String name;
    private Map<String, Double> wordOccurrences = new HashMap<>();

    public WordCollection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addWord(String word) {
        addWord(word, 1.0);
    }

    public void addWord(String word, Double value) {
        // If word exist just update number of occurrences
        Double currentValue = wordOccurrences.get(word);

        if(currentValue == null) {
            wordOccurrences.put(word, value);
        } else {
            wordOccurrences.put(word, currentValue + value);
        }
    }

    public void filterWords(List<String> wordsToFilter) {
        Map<String, Double> filteredWords = new HashMap<>();

        wordsToFilter.forEach(word -> {
            Double value = wordOccurrences.get(word);

            filteredWords.put(word, value == null ? 0.0 : value);
        });

        wordOccurrences = filteredWords;
    }

    @JsonIgnore
    @Override
    public Map<?, Double> getValues() {
        return wordOccurrences;
    }
}
