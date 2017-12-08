package project.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;


@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    @PostConstruct
    public void init() {
        repository.addUser("Lisa");
        repository.addUser("Gene");
        repository.addUser("Michael");
        repository.addUser("Claudia");
        repository.addUser("Mick");
        repository.addUser("Jack");
        repository.addUser("Toby");
    }


    public Collection<User> getUsers() {
        return repository.getUsers();
    }
}


/*
DATA SET EXAMPLE

Movie Lisa Gene Michael Claudia Mick Jack Toby
Lady in the Water 2.5 3.0 2.5 3.0 3.0
Snakes on a Plane 3.5 3.5 3.0 3.5 4.0 4.0 4.5
Just My Luck 3.0 1.5 3.0 2.0
Superman Returns 3.5 5.0 3.5 4.0 3.0 5.0 4.0
You, Me and Dupree 2.5 3.5 2.5 2.0 3.5 1.0
The Night Listener 3.0 3.0 4.0 4.5 3.0 3.0



 */