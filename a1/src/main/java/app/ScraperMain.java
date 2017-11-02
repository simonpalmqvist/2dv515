package app;

import app.wiki.crawler.Category;

import java.io.IOException;

public class ScraperMain {
    public static void main(String[] args) {
        try {
            new Category("/wiki/Programming_language", 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        Elements linkElements = doc.select("a[href]");



        Set<String> links = linkElements
                .stream()
                .map(l -> l.attr("href"))
                .filter(l -> l.matches("/wiki(/Main_Page|[^:]*)"))
                .collect(Collectors.toSet());

        System.out.println(links.size());
        links.forEach(System.out::println);

        //System.out.println(doc.body().text().replaceAll("[^a-zA-Z0-9 ]", ""));
        System.out.println(doc.body().text().replaceAll("\\[.*\\]|[^a-zA-Z0-9 ]", "").replaceAll(" +", " "));
        */
    }
}
