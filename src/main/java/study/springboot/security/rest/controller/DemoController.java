package study.springboot.security.rest.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.security.rest.support.Results;

import java.util.Map;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public Map<String, Object> demo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("demo", "demo");
        return Results.ok(data);
    }
}
