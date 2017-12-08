package project.recommendations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationsController {

    @Autowired
    RecommendationsService service;

    @GetMapping("/euclidean/user")
    public List<Recommendation> findEuclideanUserBasedRecommendation(@RequestParam(value="user", defaultValue="") String user) {
        return service.findUserBasedRecommendation(user, false);
    }

    @GetMapping("/euclidean/item")
    public List<Recommendation> findEuclideanItemBasedRecommendation(@RequestParam(value="user", defaultValue="") String user) {
        return service.findItemBasedRecommendation(user, false);
    }

    @GetMapping("/pearson/user")
    public List<Recommendation> findPearsonUserBasedRecommendation(@RequestParam(value="user", defaultValue="") String user) {
        return service.findUserBasedRecommendation(user, true);
    }

    @GetMapping("/pearson/item")
    public List<Recommendation> findPearsonItemBasedRecommendation(@RequestParam(value="user", defaultValue="") String user) {
        return service.findItemBasedRecommendation(user, true);
    }
}
