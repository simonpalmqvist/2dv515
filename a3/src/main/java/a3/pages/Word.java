package a3.pages;

import java.io.Serializable;

class Word implements Serializable {

    private final int firstPosition;
    private final int id;
    private int occurrences;

    Word(int id, int firstPosition) {
        this.id = id;
        this.firstPosition = firstPosition;
        this.occurrences = 1;
    }

    int getId() {
        return id;
    }

    int getOccurrences() {
        return occurrences;
    }

    int getFirstPosition() {
        return firstPosition;
    }

    void incrementOccurrences() {
        occurrences += 1;
    }
}
