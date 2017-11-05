package app;

import app.crawler.SiteStorage;
import app.wiki.WikiSiteCrawler;

public class ScraperMain {
    public static void main(String[] args) {
        String category1 = "/wiki/Programming_language";
        String category2 = "/wiki/Homebrewing";

        SiteStorage.store("programming", new WikiSiteCrawler(category1));
        SiteStorage.store("homebrewing", new WikiSiteCrawler(category2));
    }
}
