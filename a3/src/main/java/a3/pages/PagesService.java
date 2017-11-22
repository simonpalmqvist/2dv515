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
        if(repository.isDataReadFromDisk()) return;

        File wordFolder = ResourceUtils.getFile("classpath:data/Words/");

        // Read and add bag of words to page
        for (final File subFolder : wordFolder.listFiles()) {
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

        File linkFolder = ResourceUtils.getFile("classpath:data/Links/");

        // Read and add links to page
        for (final File subFolder : linkFolder.listFiles()) {
            if(!subFolder.isDirectory()) continue;

            for (final File file : subFolder.listFiles()) {
                Page page = repository.getPage("/wiki/" + file.getName());

                if(page != null) {
                    Files.lines(file.toPath()).map(String::trim).forEach(page::addLink);
                }
            }
        }

        // Calculate page ranks and then store all indexes to disk
        calculatePageRanks();

        repository.store();
    }

    public List<SearchResult> queryPages(String query) {
        // Split query into list of word ids
        List<Integer> queryWords = Arrays.stream(query.split(" "))
                .map(word -> repository.getIdForWord(word.toLowerCase()))
                .collect(Collectors.toList());

        List<Page> pages = repository.getPages();
        List<Double> frequency = new ArrayList<>();
        List<Double> location = new ArrayList<>();

        // Calculate content-based ranking with locations and frequency for each page
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

            // Set really high location score if no occurrences
            if(locationScore == 0.0) locationScore = 100000.0;

            frequency.add(frequencyScore);
            location.add(locationScore);
        }

        //Normalize content-based scores
        normalizeScores(frequency, false);
        normalizeScores(location, true);

        List<SearchResult> searchResults = new ArrayList<>();

        // Set weighted score for each page and filter away pages with too bad score
        for(int i = 0; i < pages.size(); i++) {
            double score = 1.0 * pages.get(i).getPageRank() + 1.0 * frequency.get(i) + 0.5 * location.get(i);

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

    private void calculatePageRanks() {
        // Calculate page rank score under 20 iterations
        for(int i = 0; i < 20; i++) {
            repository.getPages().forEach(this::calculatePageRank);
        }
    }

    private void calculatePageRank(Page page) {
        double newPageRank = 0.0;

        // Calculate page rank based on other pages links
        for(Page otherPage : repository.getPages()) {
            if(page != otherPage && otherPage.hasLinkTo(page.getName())) {
                newPageRank += otherPage.getPageRank() / (double) otherPage.getNumberOfLinks();
            }
        }

        // Set new page rank
        page.setPageRank(0.85 * newPageRank + 0.15);
    }
}
