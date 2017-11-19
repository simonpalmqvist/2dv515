package a3.pages;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PagesRepository {

    final public List<Page> pages = new ArrayList<>();
    final public Map<String, Integer> words = new HashMap<>();

    int getIdForWord(String word) {
        if(!words.containsKey(word)) words.put(word, words.size());

        return words.get(word);
    }

    Page addPage(String category, String name) {
        Page page = new Page(category, name);

        pages.add(page);

        return page;
    }
}
