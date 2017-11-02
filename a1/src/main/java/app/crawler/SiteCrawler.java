package app.crawler;

import app.wiki.WikiFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SiteCrawler {

    private Map<String, PageCrawler> pages = new HashMap<>();
    private int maxDepth;
    private String host;

    public SiteCrawler(String startPage, int depth) throws IOException {
        maxDepth = depth;
        host = startPage.substring(0, startPage.indexOf("/", 8));

        addPage(startPage.substring(startPage.indexOf("/", 8)));

        System.out.println(pages.size());
        pages.forEach((link, site) -> System.out.println(link));
    }

    private void addPage(String link) {
        addPage(link, 0);
    }

    private void addPage(String link, int depth) {
        if(pages.containsKey(link)) return;

        try {
            PageCrawler page = new PageCrawler(host + link, new WikiFilter());
            pages.put(link, page);

            if(depth < maxDepth) page.getLinks().forEach(l -> addPage(l, depth + 1));
        } catch (IOException e) {
            System.err.println("Could not fetch link: " + link);
        }
    }
}
