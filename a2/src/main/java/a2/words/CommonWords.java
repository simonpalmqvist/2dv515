package a2.words;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonWords {

    private Map<String, Double> topWords;
    private List<String> selectedWords;

    public CommonWords(Map<String, Double> words) {
        topWords = words
                .entrySet()
                .stream()
                .sorted((entryA, entryB) -> Double.compare(entryB.getValue(), entryA.getValue()))
                .limit(100)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Double> getTopWords() {
        return topWords;
    }

    public boolean hasSelectedWords() {
        return selectedWords != null;
    }

    public List<String> getSelectedWords() {
        return selectedWords;
    }

    public void setSelectedWords(List<String> selectedWords) {
        this.selectedWords = selectedWords;
    }
}
