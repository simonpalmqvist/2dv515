package a3.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class greetingController {

    @GetMapping
    public String getWelcomeText() {
        return "Woho";
    }
}
