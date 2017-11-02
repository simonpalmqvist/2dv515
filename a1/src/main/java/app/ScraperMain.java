package app;

import app.crawler.SiteCrawler;

import java.io.IOException;

public class ScraperMain {
    public static void main(String[] args) throws IOException {
        SiteCrawler page = new SiteCrawler("https://en.wikipedia.org/wiki/Programming_language", 1);


        //page.getLinks().forEach(System.out::println);
        //System.out.println(page.getLinks().size());
        //System.out.println(page.getBagOfWords());
    }
}
