package a3.pages;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Page implements Serializable {

    final private Map<Integer, Word> words = new HashMap<>();
    final private Set<String> links = new HashSet<>();
    private final String name;
    private int numberOfWords = 0;
    private double pageRank = 1.0;

    Page(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addWord(int wordId) {
        // If word already exists increment occurrences otherwise store word and position
        if(words.containsKey(wordId)) {
            words.get(wordId).incrementOccurrences();
        } else {
            words.put(wordId, new Word(wordId, numberOfWords));
        }

        // Increment number of words in page
        numberOfWords++;
    }

    Word getWord(int wordId) {
        return words.get(wordId);
    }

    void addLink(String link) {
        links.add(link);
    }

    @JsonIgnore
    public Set<String> getLinks() {
        return links;
    }

    public boolean hasLinkTo(String url) {
        return links.contains(url);
    }

    @JsonIgnore
    public int getNumberOfLinks() {
        return links.size();
    }

    public double getPageRank() {
        return pageRank;
    }

    public void setPageRank(double pageRank) {
        this.pageRank = pageRank;
    }
}
