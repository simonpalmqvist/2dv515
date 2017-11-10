package app.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageCrawler {

    private static final String HTML_TAG_REGEX = "<(script|style|title)[^>/]*>(?s).*?</(script|style|title)>|<.*?>";
    private static final String FILTER_CHARACTER_REGEX = "\\[.*\\]|[^a-zA-Z\\-_ ]";

    private String html = "";
    private Set<String> links = new LinkedHashSet<>();
    private String bagOfWords = "";

    public PageCrawler(String url, Function<String, Boolean> linkFilter) throws IOException {

        // Fetch html from url, parse links and bag of words from html content
        fetchHtml(url);
        parseLinks(linkFilter);
        parseBagOfWords();
    }

    private void fetchHtml(String url) throws IOException {
        URL pageUrl = new URL(url);

        BufferedReader pageReader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));

        String line;
        while((line = pageReader.readLine()) != null) {
            html += line + "\n";
        }
        pageReader.close();
    }

    private void parseLinks(Function<String, Boolean> linkFilter) {
        //Find all hrefs/links.
        Pattern pattern = Pattern.compile("href=\"(.*?)\"");

        Matcher matcher = pattern.matcher(html);

        while(!matcher.hitEnd()) {
            if(matcher.find()) {
                // Get matching part and get url from part
                String match = html.substring(matcher.start(), matcher.end());
                String link = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));

                // Only store links that passes link filter and remove element identifier if there is one.
                if(linkFilter.apply(link)) links.add(link.replaceFirst("#.*$", ""));
            }
        }
    }

    private void parseBagOfWords() {
        bagOfWords = html
                .replaceAll(HTML_TAG_REGEX, " ") // removes all tags and content of script, title and style tags
                .replaceAll(FILTER_CHARACTER_REGEX, "") // removes all characters that are not of interest and all text within brackets []
                .replaceAll(" +", " ") // Replace 2 or more spaces with one space
                .trim()
                .toLowerCase();
    }


    public String getHtml() {
        return html;
    }

    public Set<String> getLinks() {
        return links;
    }

    public String getBagOfWords() {
        return bagOfWords;
    }
}
