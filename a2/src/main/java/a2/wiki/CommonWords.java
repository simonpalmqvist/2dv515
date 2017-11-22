package a2.wiki;

import java.util.*;
import java.util.stream.Collectors;

public class CommonWords {

    private Map<String, Double> topWords;
    private List<String> selectedWords;

    // Words that we are not interested in using.
    public final static Set<String> FILTER_WORDS = new HashSet<>(Arrays.asList(
            "for",
            "with",
            "on",
            "by",
            "that",
            "o",
            "from",
            "was",
            "be",
            "are",
            "this",
            "which",
            "at",
            "can",
            "may",
            "also",
            "not",
            "such",
            "other",
            "used",
            "more",
            "have",
            "use",
            "wikipedia",
            "one",
            "its",
            "has",
            "first",
            "new",
            "were",
            "all",
            "page",
            "some",
            "but",
            "their",
            "articles",
            "using",
            "article",
            "b",
            "they",
            "type",
            "most",
            "isbn",
            "many",
            "–",
            "these",
            "p",
            "links",
            "time",
            "list",
            "about",
            "main",
            "when",
            "no",
            "into",
            "than",
            "example",
            "been",
            "november",
            "see",
            "only",
            "+",
            "had",
            "if",
            "two",
            "june",
            "october",
            "e",
            "create",
            "-",
            "march"
    ));

    public CommonWords(Map<String, Double> words) {
        // Filter away words that we are not interested in, sort them with most occurrences first and get the top 100
        topWords = words
                .entrySet()
                .stream()
                .sorted((entryA, entryB) -> Double.compare(entryB.getValue(), entryA.getValue()))
                .filter(entry -> !FILTER_WORDS.contains(entry.getKey()))
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
