package a2.programming;

import a2.words.WordsRepository;
import a2.words.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgrammingService extends WordsService {

    @Autowired
    private ProgrammingRepository programming;

    public ProgrammingService() {
        super("Programming");
    }

    @Override
    protected WordsRepository getRepository() {
        return programming;
    }
}
