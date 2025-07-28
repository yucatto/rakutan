package jp.kobe_u.eedept.es4.spring_app.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String getMethodName(
            @RequestParam(required = false, defaultValue = "World") String name) {
        String res = "<h1>Hello " + name + "!</h1><h2>It is \"service for データ構造論\"</h2>";
        System.out.println(res);
        return res;
    }
}
