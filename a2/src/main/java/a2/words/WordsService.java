package a2.words;


import a2.cluster.HierarchicalCluster;
import a2.cluster.KmeansCluster;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


public abstract class WordsService {

    private String category;

    abstract protected WordsRepository getRepository();

    public WordsService(String category) {
        this.category = category;
    }

    @PostConstruct
    public void init() throws IOException {
        File folder = ResourceUtils.getFile("classpath:data/Words/" + category);

        Map<String, Double> words = new LinkedHashMap<>();

        for (final File file : folder.listFiles()) {
            WordCollection wordCollection = getRepository().addWordCollection(file.getName());

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

        getRepository().setCommonWords(words);
    }

    public CommonWords getCommonWords() {
        return getRepository().getCommonWords();
    }

    public void setSelectedWords(String[] words) {
        getRepository().setSelectedWords(words);
        updateWordCollectionsWords();
        createKCluster();
        createHCluster();
    }

    private void updateWordCollectionsWords() {
        CommonWords commonWords = getRepository().getCommonWords();

        getRepository().getWordCollections()
                .forEach(c -> c.filterWords(commonWords.getSelectedWords()));
    }

    public List<Set<WordCollection>> getKCluster() {
        return getRepository().getkClusters();
    }

    protected void createKCluster() {
        getRepository().setkClusters(KmeansCluster.createCluster(getRepository().getWordCollections(), 4));
    }

    public HierarchicalCluster<WordCollection> getHCluster() {
        return getRepository().getHierarchicalCluster();
    }

    protected void createHCluster() {
        getRepository().setHierarchicalCluster(HierarchicalCluster.createClusters(getRepository().getWordCollections()));
    }
}
