package a3.pages;

import java.util.HashMap;
import java.util.Map;

class Page {

    final private Map<Integer, Word> words = new HashMap<>();
    private final String name;
    private int numberOfWords = 0;

    Page(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addWord(int wordId) {
        if(words.containsKey(wordId)) {
            words.get(wordId).incrementOccurrences();
        } else {
            words.put(wordId, new Word(wordId, numberOfWords));
        }

        numberOfWords++;
    }

    Word getWord(int wordId) {
        return words.get(wordId);
    }
}
