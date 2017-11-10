package app.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SiteStorage {

    final private static String HTML_DIR = "data/html/";
    final private static String LINKS_DIR = "data/links/";
    final private static String WORDS_DIR = "data/words/";

    public static void store(String categoryName, SiteCrawler site) {
        // Create directories if they don't exist
        createDirs(HTML_DIR + categoryName);
        createDirs(LINKS_DIR + categoryName);
        createDirs(WORDS_DIR + categoryName);

        Map<String, PageCrawler> pages = site.getPages();

        pages.forEach((link, page) -> {
            String name = link.substring(link.lastIndexOf("/"));

            // Only store links to pages that will be stored
            String links = page.getLinks()
                    .stream()
                    .filter(pages::containsKey)
                    .collect(Collectors.joining("\n"));

            // Write links and bag of words to file
            try {
                storeToFile(HTML_DIR + categoryName + "/" + name, page.getHtml());
                storeToFile(WORDS_DIR + categoryName + "/" + name, page.getBagOfWords());
                storeToFile(LINKS_DIR + categoryName + "/" + name, links);
            } catch (IOException e) {
                System.err.println("Could not store page " + name);
            }
        });
    }

    private static void storeToFile(String fileName, String content) throws IOException {
        FileWriter fWriter = new FileWriter(fileName);
        BufferedWriter bWriter = new BufferedWriter(fWriter);
        bWriter.write(content);
        bWriter.close();
        fWriter.close();
    }

    private static void createDirs(String dir) {
        File directory = new File(dir);
        if(!directory.exists()) directory.mkdirs();
    }
}
