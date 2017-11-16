package a2.games;


import a2.cluster.KmeansCluster;
import a2.words.CommonWords;
import a2.words.WordCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


@Service
public class GamesService {

    @Autowired
    private GamesRepository games;

    @PostConstruct
    public void init() throws IOException {
        File folder = ResourceUtils.getFile("classpath:data/Words/Games");

        Map<String, Double> words = new LinkedHashMap<>();

        for (final File file : folder.listFiles()) {
            WordCollection wordCollection = games.addWordCollection(file.getName());

            Files
                    .lines(file.toPath())
                    .forEach(line -> Arrays.stream(line.split(" "))
                            .forEach(word -> {
                                Double value = words.get(word);

                                wordCollection.addWord(word, value);
                                if(value != null) words.put(word, value + 1.0);
                                else              words.put(word, 1.0);
                            })
                    );
        }

        games.setCommonWords(words);
    }

    public CommonWords getCommonWords() {
        return games.getCommonWords();
    }

    public void setSelectedWords(String[] words) {
        games.setSelectedWords(words);
        updateWordCollectionsWords();
        createKCluster();
    }

    private void updateWordCollectionsWords() {
        CommonWords commonWords = games.getCommonWords();

        System.out.println(commonWords.getSelectedWords().toString());
        games.getWordCollections()
                .forEach(c -> c.filterWords(commonWords.getSelectedWords()));
    }

    public List<Set<WordCollection>> getKCluster() {
        return games.getkClusters();
    }

    private void createKCluster() {
        games.setkClusters(KmeansCluster.createCluster(games.getWordCollections(), 4));
    }
}
