package a2.games;

import a2.words.CommonWords;
import a2.words.WordCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    @Autowired
    GamesService service;

    @GetMapping("/words")
    public CommonWords commonWords() {
        return service.getCommonWords();
    }

    @PostMapping("/words")
    @ResponseStatus(HttpStatus.CREATED)
    public void pickedWords(@RequestBody String[] pickedWords) {
        if(service.getCommonWords().hasSelectedWords()) return;

        service.setSelectedWords(pickedWords);
    }

    @GetMapping("/clusters")
    public List<Set<WordCollection>> blogKClusters() {
        return service.getKCluster();
    }
}