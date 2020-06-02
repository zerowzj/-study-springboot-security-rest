package study.springboot.security.token.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.security.token.support.result.Result;
import study.springboot.security.token.support.result.Results;

@RestController
@RequestMapping("/res")
public class ResController {

    @PostMapping("/list")
    public Result list() {
        return Results.success();
    }

    @PostMapping("/add")
    public Result add() {
        return Results.success();
    }

    @PostMapping("/modify")
    public Result modify() {
        return Results.success();
    }
}
