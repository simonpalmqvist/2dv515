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

    public User addUser(String name) {
        return repository.addUser(name);
    }
}
