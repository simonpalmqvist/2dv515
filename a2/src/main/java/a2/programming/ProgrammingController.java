package a2.programming;

import a2.words.WordsController;
import a2.words.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/programming")
public class ProgrammingController extends WordsController {

    @Autowired
    ProgrammingService service;

    @Override
    protected WordsService getService() {
        return service;
    }
}