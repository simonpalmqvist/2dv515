package app.crawler;

import app.wiki.WikiFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class SiteCrawler {

    private Map<String, PageCrawler> pages = new HashMap<>();
    private int maxDepth;

    public SiteCrawler(String startPage, int depth) throws IOException {
        maxDepth = depth;

        addPage(startPage);
    }

    protected abstract String host();
    protected abstract LinkFilter linkFilter();


    private void addPage(String link) {
        addPage(link, 0);
    }

    private void addPage(String link, int depth) {
        if(pages.containsKey(link)) return;

        try {
            PageCrawler page = new PageCrawler(host() + link, linkFilter());
            pages.put(link, page);

            if(depth < maxDepth) page.getLinks().forEach(l -> addPage(l, depth + 1));
        } catch (IOException e) {
            System.err.println("Could not fetch link: " + link);
        }
    }

    public Map<String, PageCrawler> getPages() {
        return pages;
    }
}
