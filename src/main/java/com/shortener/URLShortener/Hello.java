package com.shortener.URLShortener;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Hello {

    @GetMapping("/")
    public String greet(){
        System.out.println("Hello, this is url shortener app!!");
        return "Hello, this is url shortener app!!";
    }
}
