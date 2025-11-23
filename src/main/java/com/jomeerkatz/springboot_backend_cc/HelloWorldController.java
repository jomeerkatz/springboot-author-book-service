package com.jomeerkatz.springboot_backend_cc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "hello world";
    }
}
