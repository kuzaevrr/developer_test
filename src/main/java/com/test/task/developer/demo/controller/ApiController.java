package com.test.task.developer.demo.controller;


import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final Service service;

    public ApiController(Service service) {
        this.service = service;
    }

    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployee() {

        List<Employee> employeeList = service.allEmployees();
        List<Task> tasks = service.allTasks();

        List<Employee> employeesResult = new ArrayList<>();
        for(Employee employee: employeeList) {
            int numberTasks =0;
            for (Task task : tasks) {
                if (task.getEmployeeId() == employee.getId()){
                    numberTasks++;
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
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                return task.getPriority().compareTo(t1.getPriority());
            }
        }.reversed());

        return tasks;
    }

    @GetMapping("/getTask/{task_id}")
    public Task getTask(@PathVariable int task_id){
        return service.getTasksById(task_id);
    }

    @GetMapping("/getEmployee/{emp_id}")
    public Employee getEmployee(@PathVariable int emp_id){
        return service.getEmployeeById(emp_id);
    }

    @GetMapping("get_sort_up")
    public List<Employee> getSortUp(){
        return getAllEmployee();
    }
}
