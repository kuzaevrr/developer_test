package com.test.task.developer.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    @GetMapping("/developer-test")
    public String indexView(){
        return "index";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}