package a2.cluster;


import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PearsonTests {

    private Map<String, TestUser> users;

    @Before
    public void setup() {
        users = new HashMap<>();

        TestUser toby = new TestUser("toby");
        toby.addRating("B", 4.5);
        toby.addRating("D", 4.0);
        toby.addRating("E", 1.0);

        TestUser lisa = new TestUser("lisa");
        lisa.addRating("A", 2.5);
        lisa.addRating("B", 3.5);
        lisa.addRating("C", 3.0);
        lisa.addRating("D", 3.5);
        lisa.addRating("E", 2.5);
        lisa.addRating("F", 3.0);

        TestUser claudia = new TestUser("claudia");
        claudia.addRating("B", 3.5);
        claudia.addRating("C", 3.0);
        claudia.addRating("D", 4.0);
        claudia.addRating("E", 2.5);
        claudia.addRating("F", 4.5);

        users.put(toby.getName(), toby);
        users.put(lisa.getName(), lisa);
        users.put(claudia.getName(), claudia);
    }

    @Test
    public void shouldReturnCorrectScore() {
        TestUser toby = users.get("toby");
        TestUser lisa = users.get("lisa");
        TestUser claudia = users.get("claudia");

        double score1 = Pearson.calculateScore(toby.getRatings(), lisa.getRatings());
        double score2 = Pearson.calculateScore(toby.getRatings(), claudia.getRatings());

        assertEquals(0.991, score1, 0.001);
        assertEquals(0.893, score2, 0.001);
    }
}
