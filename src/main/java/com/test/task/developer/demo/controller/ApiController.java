package com.test.task.developer.demo.controller;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return employeeList;
    }

}
