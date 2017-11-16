package a2.games;

import a2.words.WordsController;
import a2.words.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GamesController extends WordsController {

    @Autowired
    GamesService service;

    @Override
    protected WordsService getService() {
        return service;
    }
}