package app;

import app.crawler.SiteCrawler;
import app.crawler.SiteStorage;
import app.wiki.WikiSiteCrawler;

import java.io.IOException;

public class ScraperMain {
    public static void main(String[] args) throws IOException {
        String category1 = "/wiki/Programming_language";
        String category2 = "/wiki/Video_game";
        int maxDepth = 1;

        SiteStorage.store("programming", new WikiSiteCrawler(category1, maxDepth));
        SiteStorage.store("gaming", new WikiSiteCrawler(category2, maxDepth));
    }
}
