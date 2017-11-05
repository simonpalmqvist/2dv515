package app.wiki;

import app.crawler.LinkFilter;
import app.crawler.SiteCrawler;

import java.io.IOException;

public class WikiSiteCrawler extends SiteCrawler {

    public WikiSiteCrawler(String startPage, int depth) throws IOException {
        super(startPage, depth);
    }

    @Override
    protected String host() {
        return "https://en.wikipedia.org";
    }

    @Override
    protected LinkFilter linkFilter() {
        return new WikiFilter();
    }
}
