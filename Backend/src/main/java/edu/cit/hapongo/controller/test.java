package edu.cit.hapongo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    
    @RequestMapping("/")
    public String hello() {
        return "Hello Felraine Cataos";
    }
}
