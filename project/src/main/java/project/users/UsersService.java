package project.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;


@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    public Collection<User> getUsers() {
        return repository.getUsers();
    }
}
