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

    Collection<User> getUsers() {
        return users.values();
    }
}
