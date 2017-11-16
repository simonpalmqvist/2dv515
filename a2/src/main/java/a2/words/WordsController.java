package a2.words;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


abstract public class WordsController {

    protected abstract WordsService getService();

    @GetMapping("/words")
    public CommonWords commonWords() {
        return getService().getCommonWords();
    }

    @PostMapping("/words")
    @ResponseStatus(HttpStatus.CREATED)
    public void pickedWords(@RequestBody String[] pickedWords) {
        if(getService().getCommonWords().hasSelectedWords()) return;

        getService().setSelectedWords(pickedWords);
    }

    @GetMapping("/k-clusters")
    public List<Set<WordCollection>> wordKClusters() {
        return getService().getKCluster();
    }
}