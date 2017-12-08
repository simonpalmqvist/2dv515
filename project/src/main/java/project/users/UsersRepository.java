package project.users;

import org.springframework.stereotype.Repository;
import project.ratings.Rating;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UsersRepository {

    final private Map<String, User> users = new HashMap<>();

    public User addUser(String name) {
        User user = new User(name);

        users.put(name, user);

        return user;
    }

    Collection<User> getUsers() {
        return users.values();
    }
}
