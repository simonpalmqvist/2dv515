package app.wiki.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Category {

    private Map<String, Site> sites = new HashMap();

    public Category(String category, int depth) throws IOException {
        Site categorySite = new Site(category);

        for( String link : categorySite.getLinks()) {
            if(!sites.containsKey(link)) sites.put(link, new Site(link));
        }

        System.out.println(sites.size());
        sites.forEach((link, site) -> System.out.println(site.getBagOfWords()));
    }
}
