package com.test.task.developer.demo.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/developer-test")
    public String home(Model model){

        return "main";
    }

}
