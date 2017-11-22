package a3.pages;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PagesRepository {

    final private boolean dataReadFromDisk;
    final private Storage wordStorage = new Storage("words");
    final private Storage pageStorage = new Storage("pages");
    final private Map<String, Page> nameToPage = new HashMap<>(); // Used for faster lookup
    final private List<Page> pages;
    final private Map<String, Integer> words;


    PagesRepository() throws Exception {
        // If indexes are already stored on file then read and add them to memory
        if (wordStorage.exists() && pageStorage.exists()) {
            words = (Map<String, Integer>) wordStorage.read();
            pages = (List<Page>) pageStorage.read();
            pages.forEach(p -> nameToPage.put(p.getName(), p));
            dataReadFromDisk = true;
        } else {
            this.words = new HashMap<>();
            this.pages = new ArrayList<>();
            dataReadFromDisk = false;
        }
    }

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

    void store() {
        wordStorage.save(words);
        pageStorage.save(pages);
    }

    public boolean isDataReadFromDisk() {
        return dataReadFromDisk;
    }
}
