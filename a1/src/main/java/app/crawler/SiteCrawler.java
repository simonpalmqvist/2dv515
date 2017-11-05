package app.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class SiteCrawler {

    private Map<String, PageCrawler> pages = new HashMap<>();
    private String siteHost;
    private int maxDepth;
    private int maxPages;

    public SiteCrawler(String host, String startPage, int depth, int limit) {
        siteHost = host;
        maxDepth = depth;
        maxPages = limit;

        // Start crawling start page
        Set<String> linksToVisit = new LinkedHashSet<>();
        linksToVisit.add(startPage);
        addPages(linksToVisit, 0);
    }

    protected abstract boolean linkFilter(String link); // Determines which links that should be crawled

    private void addPages(Set<String> linksToVisit, int depth) {
        Set<String> nextLinksToVisit = new LinkedHashSet<>();

        // Visit all links and store pages
        for(String l : linksToVisit) {
            if(pages.size() >= maxPages) return; // Stop when limit is reached
            addPage(l, nextLinksToVisit);
        }

        if(depth < maxDepth) addPages(nextLinksToVisit, ++depth); // Don't go deeper than max depth
    }

    private void addPage(String link, Set<String> nextLinksToVisit) {
        // Don't visit already visited links
        if(pages.containsKey(link.toLowerCase())) return;

        // Crawl page and add result to pages
        try {
            PageCrawler page = new PageCrawler(siteHost + link, this::linkFilter);
            pages.put(link.toLowerCase(), page);

            // Store links on page in links to next be visisted list
            page.getLinks().forEach(nextLinksToVisit::add);
        } catch (IOException e) {
            System.err.println("Could not fetch link: " + link);
        }
    }

    public Map<String, PageCrawler> getPages() {
        return pages;
    }
}
