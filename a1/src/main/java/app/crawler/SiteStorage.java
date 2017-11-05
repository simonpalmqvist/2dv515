package app.crawler;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SiteStorage {

    final private static String LINKS_DIR = "data/links/";
    final private static String WORDS_DIR = "data/words/";

    public static void store(String categoryName, SiteCrawler site) {
        createDirs(LINKS_DIR + categoryName);
        createDirs(WORDS_DIR + categoryName);

        site.getPages().forEach((link, page) -> {
            String name = link.substring(link.lastIndexOf("/"));

            try {
                storeToFile(WORDS_DIR + categoryName + "/" + name, page.getBagOfWords());
                storeToFile(LINKS_DIR + categoryName + "/" + name, StringUtils.join(page.getLinks(), "\n"));

            } catch (IOException e) {
                System.err.println("Could not store file " + name);
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
