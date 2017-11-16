package a2.blogs;

import a2.cluster.HierarchicalCluster;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class BlogRepository {

    private final Set<Blog> blogs = new HashSet<>();
    private List<Set<Blog>> kClusters;
    private HierarchicalCluster<Blog> hierarchicalCluster;

    public Blog createBlog(String name) {
        Blog blog = new Blog(name);

        blogs.add(blog);

        return blog;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public List<Set<Blog>> getKClusters() {
        return kClusters;
    }

    public void setKClusters(List<Set<Blog>> kCluster) {
        this.kClusters = kCluster;
    }

    public HierarchicalCluster<Blog> getHierarchicalCluster() {
        return hierarchicalCluster;
    }

    public void setHierarchicalCluster(HierarchicalCluster<Blog> cluster) {
        this.hierarchicalCluster = cluster;
    }


}