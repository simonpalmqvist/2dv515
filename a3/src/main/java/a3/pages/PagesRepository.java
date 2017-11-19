package a3.pages;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PagesRepository {

    final private Map<String, Page> nameToPage = new HashMap<>();
    final private List<Page> pages = new ArrayList<>();
    final private Map<String, Integer> words = new HashMap<>();

    int getIdForWord(String word) {
        if(!words.containsKey(word)) words.put(word, words.size());

        return words.get(word);
    }

    Page addPage(String name) {
        Page page = new Page(name);

        pages.add(page);
        nameToPage.put(name, page);

        return page;
    }

    List<Page> getPages() {
        return pages;
    }

    Page getPage(String name) {
        return nameToPage.get(name);
    }
}
