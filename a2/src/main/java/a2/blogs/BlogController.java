package a2.blogs;

import a2.cluster.HierarchicalCluster;
import a2.cluster.WordCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    BlogService service;

    @RequestMapping("/k-clusters")
    public List<Set<WordCollection>> blogKClusters() {
        return service.getKCluster();
    }

    @RequestMapping("/h-clusters")
    public HierarchicalCluster<WordCollection> blogHClusters() {
        return service.getHCluster();
    }
}
