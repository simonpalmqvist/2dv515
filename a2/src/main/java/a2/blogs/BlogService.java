package a2.blogs;


import a2.cluster.KmeansCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogs;

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
                    Blog blog = blogs.createBlog(data[0]);
                    for(int i = 1; i < data.length; i++) blog.addWord(items.get(0)[i], Double.parseDouble(data[i]));
        });

        blogs.setKClusters(KmeansCluster.createCluster(blogs.getBlogs(), 4));

        blogs.getKClusters().forEach(cluster -> {
            System.out.println("\n\nCluster");
            cluster.forEach(blog -> System.out.println(blog.getName()));
        });
    }
}
