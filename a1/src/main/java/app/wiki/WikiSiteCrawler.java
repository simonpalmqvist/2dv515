package app.wiki;

import app.crawler.SiteCrawler;

public class WikiSiteCrawler extends SiteCrawler {

    public WikiSiteCrawler(String startPage) {
        // don't go deeper than 2 links from start and max 1000 pages
        super("https://en.wikipedia.org", startPage, 2, 1000);
    }

    @Override
    protected boolean linkFilter(String link) {
        // We are only interested in links that starts with /wiki/ and don't contain ':' (Special pages). also main page is excluded
        return link.matches("/wiki/[^:]*") && !link.equals("/wiki/Main_Page");
    }
}
