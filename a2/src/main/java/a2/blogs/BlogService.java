package a2.blogs;


import a2.cluster.HierarchicalCluster;
import a2.cluster.KmeansCluster;
import a2.cluster.WordCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    private BlogRepository repository;

    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:blogdata.txt");

        // Read in data from blogdata file and store as word collections
        List<String[]> items = Files
                .lines(file.toPath())
                .map(line -> line.split("\\t"))
                .collect(Collectors.toList());

        items
                .stream()
                .skip(1)
                .forEach(data -> {
                    WordCollection wordCollection = repository.addWordCollection(data[0]);
                    for(int i = 1; i < data.length; i++) wordCollection.addWord(items.get(0)[i], Double.parseDouble(data[i]));
        });

        // create clusters from word collections
        createKCluster();
        createHCluster();
    }

    public List<Set<WordCollection>> getKCluster() {
        return repository.getkClusters();
    }

    protected void createKCluster() {
        repository.setkClusters(KmeansCluster.createCluster(repository.getWordCollections(), 4));
    }

    public HierarchicalCluster<WordCollection> getHCluster() {
        return repository.getHierarchicalCluster();
    }

    protected void createHCluster() {
        repository.setHierarchicalCluster(HierarchicalCluster.createClusters(repository.getWordCollections()));
    }
}
