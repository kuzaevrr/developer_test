package com.test.task.developer.demo.controllers;


import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.Service;
import com.test.task.developer.demo.sorting.Sort;
import liquibase.pro.packaged.S;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
                if (task.getEmployeeId().equals(employee.getId())){
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

    @PostMapping("/saveEmp")
    public Employee saveEmp(@RequestBody Employee employee){
        if (employee.getLeader() == 0) {
            employee.setLeader(null);
        }
        if (employee.getId() != 0) {
            service.updateEmployee(employee);
        } else {
            service.setEmployee(employee);
        }
        return employee;
    }

    @PostMapping("/saveTask")
    public Task saveTask(@RequestBody Task task){
        if(task.getId() != 0){
            service.updateTask(task);
        }else{
            service.setTask(task);
        }
        return task;
    }

    @PostMapping("/deleteEmp")
    public void deleteEmp(@RequestBody int id){
        service.deleteEmployee(id);
    }

    @PostMapping("/deleteTask")
    public void deleteTask(@RequestBody int id){
        service.deleteTask(id);
    }

    @PostMapping("/getSort")
    public List getSortUp(@RequestBody Map<String, Integer> map){

        if(map.get("empOfTask") == 1){
            Sort<Employee> sort = new Sort(getAllEmployee());
            return  sort.sortList(map);
        }else{
            Sort<Task> sort = new Sort(getAllTasks());
            return sort.sortList(map);
        }
    }


    private void sort(String key, Integer value){

    }
}
