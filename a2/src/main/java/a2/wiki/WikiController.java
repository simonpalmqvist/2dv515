package a2.wiki;

import a2.cluster.HierarchicalCluster;
import a2.cluster.WordCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/wiki")
public class WikiController {

    @Autowired
    WikiService service;

    @GetMapping
    public CommonWords commonWords() {
        return service.getCommonWords();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void pickedWords(@RequestBody String[] pickedWords) {
        if(service.getCommonWords().hasSelectedWords()) return;

        service.setSelectedWords(pickedWords);
    }

    @GetMapping("/k-clusters")
    public List<Set<WordCollection>> getKClusters() {
        return service.getKCluster();
    }

    @GetMapping("/h-clusters")
    public HierarchicalCluster<WordCollection> getHClusters() {
        return service.getHCluster();
    }
}
