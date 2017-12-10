package project.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    public Collection<User> getUsers() {
        return repository.getUsers();
    }

    public User getUser(int id) {
        return repository.getUser(id);
    }

    public User addUser(int id, String name) {
        return repository.hasUser(id) ? repository.getUser(id) : repository.addUser(id, name);
    }
}
