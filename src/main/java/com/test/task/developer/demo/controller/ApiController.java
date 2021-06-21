package com.test.task.developer.demo.controller;


import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private Service service;

    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployee() {

        List<Employee> employeeList = service.allEmployees();
        List<Task> tasks = service.allTasks();

        List<Employee> employeesResult = new ArrayList<>();
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
            employee.setNumberTasks(numberTasks);
            employeesResult.add(employee);
        }
        return employeesResult;
    }

    @GetMapping("/allTasks")
    public List<Task> getAllTasks(){
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

        return tasks;
    }
}
