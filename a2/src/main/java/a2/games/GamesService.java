package a2.games;

import a2.words.WordsRepository;
import a2.words.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamesService extends WordsService {

    @Autowired
    private GamesRepository games;

    public GamesService() {
        super("Games");
    }

    @Override
    protected WordsRepository getRepository() {
        return games;
    }
}
