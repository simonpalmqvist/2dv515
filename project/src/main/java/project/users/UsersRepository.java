package project.users;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UsersRepository {

    final private Map<Integer, User> users = new HashMap<>();

    User addUser(int id, String name) {
        User user = new User(id, name);

        users.put(id, user);

        return user;
    }

    boolean hasUser(int id) {
        return users.containsKey(id);
    }

    User getUser(int id) {
        return users.get(id);
    }

    Collection<User> getUsers() {
        return users.values();
    }
}
