package a2.blogs;


import a2.cluster.HierarchicalCluster;
import a2.cluster.KmeansCluster;
import a2.words.WordCollection;
import a2.words.WordsRepository;
import a2.words.WordsService;
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
public class BlogService extends WordsService {

    @Autowired
    private BlogRepository blogs;

    public BlogService() {
        super("Blogs");
    }

    @Override
    protected WordsRepository getRepository() {
        return blogs;
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:blogdata.txt");

        List<String[]> items = Files
                .lines(file.toPath())
                .map(line -> line.split("\\t"))
                .collect(Collectors.toList());

        items
                .stream()
                .skip(1)
                .forEach(data -> {
                    WordCollection wordCollection = getRepository().addWordCollection(data[0]);
                    for(int i = 1; i < data.length; i++) wordCollection.addWord(items.get(0)[i], Double.parseDouble(data[i]));
        });

        createKCluster();
        createHCluster();
    }
}
