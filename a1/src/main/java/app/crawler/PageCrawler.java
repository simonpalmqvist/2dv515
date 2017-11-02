package app.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PageCrawler {

    private String html = "";
    private Set<String> links = new HashSet<>();
    private String bagOfWords = "";

    PageCrawler(String url, LinkFilter linkFilter) throws IOException {
        URL pageUrl = new URL(url);

        BufferedReader pageReader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));

        String line;
        while((line = pageReader.readLine()) != null) {
            html += line + "\n";
        }
        pageReader.close();

        Pattern pattern = Pattern.compile("href=\"(.*?)\"");

        Matcher matcher = pattern.matcher(html);

        while(!matcher.hitEnd()) {
            if(matcher.find()) {
                String match = html.substring(matcher.start(), matcher.end());

                String link = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));

                if(linkFilter.matches(link)) links.add(link.replaceFirst("#.*$", ""));
            }
        }

        bagOfWords = html
                .substring(html.indexOf("id=\"mw-content-text\""), html.indexOf("id=\"mw-navigation\""))
                .replaceAll("^[^<]*?>|<[^>]*?$|<.*?>", " ")
                .replaceAll("\\[.*\\]|[^a-zA-Z0-9\\- ]", "")
                .replaceAll(" +", " ")
                .trim()
                .toLowerCase();


    }

    String getHtml() {
        return html;
    }

    Set<String> getLinks() {
        return links;
    }

    String getBagOfWords() {
        return bagOfWords;
    }
}
