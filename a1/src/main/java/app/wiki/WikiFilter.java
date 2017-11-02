package app.wiki;

import app.crawler.LinkFilter;

public class WikiFilter implements LinkFilter {

    @Override
    public boolean matches(String link) {
        return link.matches("/wiki(/Main_Page|[^:]*)");
    }
}
