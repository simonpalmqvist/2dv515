package a3.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PagesService {

    @Autowired
    private PagesRepository repository;

    @PostConstruct
    public void init() throws IOException {
        File folder = ResourceUtils.getFile("classpath:data/Words/");

        for (final File subFolder : folder.listFiles()) {
            if(!subFolder.isDirectory()) continue;

            for (final File file : subFolder.listFiles()) {

                Page page = repository.addPage("/wiki/" + file.getName());

                Files
                        .lines(file.toPath())
                        .forEach(line -> Arrays.stream(line.split(" "))
                                .forEach(word -> page.addWord(repository.getIdForWord(word)))
                        );
            }
        }
    }

    public List<SearchResult> queryPages(String query) {
        List<Integer> queryWords = Arrays.stream(query.split(" "))
                .map(word -> repository.getIdForWord(word.toLowerCase()))
                .collect(Collectors.toList());

        List<Page> pages = repository.getPages();
        List<Double> frequency = new ArrayList<>();
        List<Double> location = new ArrayList<>();

        for(int i = 0; i < pages.size(); i++) {
            Page page = pages.get(i);

            double frequencyScore = 0.0;
            double locationScore = 0.0;

            for(int queryId : queryWords) {
                Word word = page.getWord(queryId);

                if(word != null) {
                    frequencyScore += word.getOccurrences();
                    locationScore += word.getFirstPosition();
                }
            }

            if(locationScore == 0.0) locationScore = 100000.0;

            frequency.add(frequencyScore);
            location.add(locationScore);
        }

        normalizeScores(frequency, false);
        normalizeScores(location, true);

        List<SearchResult> searchResults = new ArrayList<>();

        for(int i = 0; i < pages.size(); i++) {
            double score = 1.0 * frequency.get(i) + 0.5 * location.get(i);

            if(score > 0.001) searchResults.add(new SearchResult(pages.get(i), score));
        }

        Collections.sort(searchResults);

        return searchResults;
    }

    private void normalizeScores(List<Double> scores, boolean smallIsGood) {
        if(smallIsGood) {
            double minScore = Collections.min(scores);

            for(int i = 0; i < scores.size(); i++) {
                scores.set(i, minScore / Math.max(scores.get(i), 0.00001));
            }
        } else {
            double maxScore = Math.max(Collections.max(scores), 0.00001);

            for(int i = 0; i < scores.size(); i++) scores.set(i, scores.get(i) / maxScore);
        }
    }
}
