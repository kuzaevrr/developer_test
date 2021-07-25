package com.test.task.developer.demo.controllers;


import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.ServiceDBJooq;
import com.test.task.developer.demo.sorting.Sort;
import com.test.task.developer.demo.sorting.comparators.tasks.PriorityTaskComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private  ServiceDBJooq service;

    @GetMapping("/search")
    public Page<Employee> findBySearchTerm(@RequestParam("searchTerm")String searchTerm, Pageable pageable){
        return service.findBySearchTerm(searchTerm, pageable);
    }


    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployee() {
        return service.allEmployees();
    }

    @GetMapping("/allTasks")
    public List<Task> getAllTasks(){
        return service.allTasks();
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
