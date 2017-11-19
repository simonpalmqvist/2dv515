package a3.pages;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PageController {

    @Autowired
    PagesService service;

    @GetMapping
    public List<SearchResult> greeting(@RequestParam(value="query", defaultValue="World") String query) {
        return service.queryPages(query);
    }

}
