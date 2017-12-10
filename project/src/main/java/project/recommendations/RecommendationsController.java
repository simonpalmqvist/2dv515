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
    public List<Recommendation> findEuclideanUserBasedRecommendation(@RequestParam(value="user", defaultValue="") int userId) {
        return service.findUserBasedRecommendation(userId, false);
    }

    @GetMapping("/euclidean/item")
    public List<Recommendation> findEuclideanItemBasedRecommendation(@RequestParam(value="user", defaultValue="") int userId) {
        return service.findItemBasedRecommendation(userId, false);
    }

    @GetMapping("/pearson/user")
    public List<Recommendation> findPearsonUserBasedRecommendation(@RequestParam(value="user", defaultValue="") int userId) {
        return service.findUserBasedRecommendation(userId, true);
    }

    @GetMapping("/pearson/item")
    public List<Recommendation> findPearsonItemBasedRecommendation(@RequestParam(value="user", defaultValue="") int userId) {
        return service.findItemBasedRecommendation(userId, true);
    }
}
