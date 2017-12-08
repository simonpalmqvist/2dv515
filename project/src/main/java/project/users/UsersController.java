package project.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    UsersService service;

    @GetMapping("/users")
    public Collection<User> greeting() {
        return service.getUsers();
    }
}
