package a2.words;

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

    public void addWord(String word, Double value) {
        Double currentValue = wordOccurrences.get(word);

        if(currentValue == null) {
            wordOccurrences.put(word, 1.0);
        } else {
            wordOccurrences.put(word, currentValue + 1.0);
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
