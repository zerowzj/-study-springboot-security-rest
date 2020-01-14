package study.springboot.security.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/sayHi")
    public String sayHi() {
        return "sayHi";
    }

    @GetMapping("/sayBye")
    public String sayBye() {
        return "sayBye";
    }
}
