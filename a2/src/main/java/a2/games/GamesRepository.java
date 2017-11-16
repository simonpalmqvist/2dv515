package a2.games;

import a2.cluster.KmeansCluster;
import a2.words.CommonWords;
import a2.words.WordCollection;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class GamesRepository {

    private CommonWords commonWords;
    final private List<WordCollection> wordCollections = new ArrayList<>();
    private List<Set<WordCollection>> kClusters;

    public CommonWords getCommonWords() {
        return commonWords;
    }

    public void setCommonWords(Map<String, Double> words) {
        this.commonWords = new CommonWords(words);
    }

    public void setSelectedWords(String[] selectedWords) {
        this.commonWords.setSelectedWords(Arrays.asList(selectedWords));
    }


    public List<WordCollection> getWordCollections() {
        return wordCollections;
    }

    public WordCollection addWordCollection(String name) {
        WordCollection wordCollection = new WordCollection(name);
        wordCollections.add(wordCollection);

        return wordCollection;
    }

    public List<Set<WordCollection>> getkClusters() {
        return kClusters;
    }

    public void setkClusters(List<Set<WordCollection>> kClusters) {
        this.kClusters = kClusters;
    }
}