package a2.blogs;

import a2.cluster.PearsonItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class Blog implements PearsonItem {

    private final String name;
    private final Map<String, Double> wordOccurrences = new HashMap<>();

    public Blog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public Map<String, Double> getWordOccurrences() {
        return wordOccurrences;
    }

    public void addWord(String word, Double occurrences) {
        wordOccurrences.put(word, occurrences);
    }

    @JsonIgnore
    @Override
    public Map<String, Double> getValues() {
        return getWordOccurrences();
    }
}