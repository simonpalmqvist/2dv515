package a3.pages;

class Word {

    private final int firstOccurrence;
    private final int id;
    private int occurrences;

    Word(int id, int firstOccurrence) {
        this.id = id;
        this.firstOccurrence = firstOccurrence;
        this.occurrences = 1;
    }

    int getId() {
        return id;
    }

    int getOccurrences() {
        return occurrences;
    }

    int getFirstOccurrence() {
        return firstOccurrence;
    }

    void incrementOccurrences() {
        occurrences += 1;
    }
}
