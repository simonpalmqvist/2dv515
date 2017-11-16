package a2.blogs;

import a2.cluster.HierarchicalCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class BlogController {

    @Autowired
    BlogService service;

    @RequestMapping("/api/blogs")
    public Set<Blog> blogs() {
        return service.getBlogs();
    }

    @RequestMapping("/api/blogs/k-clusters")
    public List<Set<Blog>> blogKClusters() {
        return service.getBlogKClusters();
    }

    @RequestMapping("/api/blogs/h-clusters")
    public HierarchicalCluster<Blog> blogHClusters() {
        return service.getBlogHClusters();
    }
}