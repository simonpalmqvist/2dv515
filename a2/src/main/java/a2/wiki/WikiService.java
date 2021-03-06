package a2.wiki;


import a2.cluster.HierarchicalCluster;
import a2.cluster.KmeansCluster;
import a2.cluster.WordCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class WikiService {

    @Autowired
    WikiRepository repository;

    @PostConstruct
    public void init() throws IOException {
        File folder = ResourceUtils.getFile("classpath:data/Words/");

        Map<String, Double> words = new LinkedHashMap<>();

        // Go through each file in path and create a word collection, also store all words with how many times they occur in a map
        for (final File subFolder : folder.listFiles()) {
            if(!subFolder.isDirectory()) continue;
            for (final File file : subFolder.listFiles()) {
                WordCollection wordCollection = repository.addWordCollection(file.getName());

                Files
                        .lines(file.toPath())
                        .forEach(line -> Arrays.stream(line.split(" "))
                                .forEach(word -> {
                                    wordCollection.addWord(word);

                                    Double value = words.get(word);
                                    if(value != null) words.put(word, value + 1.0);
                                    else              words.put(word, 1.0);
                                })
                        );
            }
        }

        // Set all words that there is in common
        repository.setCommonWords(words);
    }

    public CommonWords getCommonWords() {
        return repository.getCommonWords();
    }

    public void setSelectedWords(String[] words) {
        // Store selected words, filter away all words that are not selected in collections and create clusters
        repository.setSelectedWords(words);
        updateWordCollectionsWords();
        createKCluster();
        createHCluster();
    }

    private void updateWordCollectionsWords() {
        CommonWords commonWords = repository.getCommonWords();

        repository.getWordCollections()
                .forEach(c -> c.filterWords(commonWords.getSelectedWords()));
    }

    public List<Set<WordCollection>> getKCluster() {
        return repository.getkClusters();
    }

    protected void createKCluster() {
        // Create 4 K-means clusters
        repository.setkClusters(KmeansCluster.createCluster(repository.getWordCollections(), 4));
    }

    public HierarchicalCluster<WordCollection> getHCluster() {
        return repository.getHierarchicalCluster();
    }

    protected void createHCluster() {
        repository.setHierarchicalCluster(HierarchicalCluster.createClusters(repository.getWordCollections()));
    }
}
