package com.test.task.developer.demo.controller;


import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/developer-test")
    public String home(Model model){

        return "main";
    }

}
