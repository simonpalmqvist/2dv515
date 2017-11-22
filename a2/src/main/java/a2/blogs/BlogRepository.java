package a2.blogs;

import a2.cluster.HierarchicalCluster;
import a2.cluster.WordCollection;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BlogRepository {
    final private List<WordCollection> wordCollections = new ArrayList<>();
    private List<Set<WordCollection>> kClusters;
    private HierarchicalCluster<WordCollection> hierarchicalCluster;


    public List<WordCollection> getWordCollections() {
        return wordCollections;
    }

    public WordCollection addWordCollection(String name) {
        WordCollection wordCollection = new WordCollection(name);
        wordCollections.add(wordCollection);

        return wordCollection;
    }

    public List<Set<WordCollection>> getkClusters() {
        return kClusters;
    }

    public void setkClusters(List<Set<WordCollection>> kClusters) {
        this.kClusters = kClusters;
    }

    public HierarchicalCluster<WordCollection> getHierarchicalCluster() {
        return hierarchicalCluster;
    }

    public void setHierarchicalCluster(HierarchicalCluster<WordCollection> hClusters) {
        this.hierarchicalCluster = hClusters;
    }
}
