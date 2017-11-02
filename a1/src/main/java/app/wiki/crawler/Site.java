package app.wiki.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

class Site {

    private static String URL = "http://en.wikipedia.org";
    private Set<String> links;
    private String html;
    private String bagOfWords;

    public Site(String pageUrl) throws IOException {
        Document doc = Jsoup.connect(URL + pageUrl).get();

        parseLinks(doc);
        parseText(doc);
        html = doc.toString();
    }

    private void parseLinks(Document doc) {
        Elements linkElements = doc.select("a[href]");

        links = linkElements
                .stream()
                .map(l -> l.attr("href"))
                .filter(l -> l.matches("/wiki(/Main_Page|[^:]*)"))
                .collect(Collectors.toSet());
    }

    private void parseText(Document doc) {
        bagOfWords = doc
                .body()
                .text()
                .replaceAll("\\[.*\\]|[^a-zA-Z0-9 ]", "")
                .replaceAll(" +", " ");
    }

    public Set<String> getLinks() {
        return links;
    }

    public String getBagOfWords() {
        return bagOfWords;
    }

    public String getHtml() {
        return html;
    }
}
