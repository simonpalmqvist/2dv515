package a3.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class PagesService {

    @Autowired
    private PagesRepository repository;

    @PostConstruct
    public void init() throws IOException {
        File folder = ResourceUtils.getFile("classpath:data/Words/");

        for (final File subFolder : folder.listFiles()) {
            if(!subFolder.isDirectory()) continue;

            for (final File file : subFolder.listFiles()) {

                Page page = repository.addPage(subFolder.getName(), file.getName());

                Files
                        .lines(file.toPath())
                        .forEach(line -> Arrays.stream(line.split(" "))
                                .forEach(word -> page.addWord(repository.getIdForWord(word)))
                        );
            }
        }

        repository.pages.forEach((p) -> System.out.println(p.getCategory() + ": " + p.getName()));
    }
}
