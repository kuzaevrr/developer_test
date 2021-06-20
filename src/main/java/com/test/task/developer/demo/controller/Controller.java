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

    @Autowired
    private Service service;


    @GetMapping("/employees")
    public String home(Model model){

        List<Employee> employeeList = service.allEmployees();
        List<Task> tasks = service.allTasks();
        for(Employee employee: employeeList) {
            int numberTasks =0;
            for (Task task : tasks) {
                if (task.getEmployee_id() == employee.getId()){
                    numberTasks++;
                }
            }
            for(Employee employee2: employeeList) {
                if (employee.getLeader() == employee2.getId()){
                    employee.setLeaderName(employee2.getFull_name());
                    break;
                }
            }
            employee.setNumber_tasks(numberTasks);
        }
        model.addAttribute("allEmployees", employeeList);
        return "main";
    }


    @GetMapping("/tasks")
    public String tasks(Model model){

        List<Task> tasks = service.allTasks();
        List<Employee> employeeList = service.allEmployees();

        for(Task task: tasks){
            for(Employee employee: employeeList){
                if(task.getEmployee_id() == employee.getId()){
                    task.setNameEmployee(employee.getFull_name());
                    break;
                }
            }
        }
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                return task.getPriority().compareTo(t1.getPriority());
            }
        }.reversed());

        model.addAttribute("allTasks", tasks);
        return "tasks";
    }

}
