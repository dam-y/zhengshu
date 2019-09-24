package com.cxria.ceshi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {
    @GetMapping("hello")
    public String hello(){
        return "hello,boy";
    }

    @PostMapping("hello2")
    public String hello2(){
        return "hello,girl";
    }
}
