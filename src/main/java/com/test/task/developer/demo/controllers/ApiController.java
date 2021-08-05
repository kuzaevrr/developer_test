package com.test.task.developer.demo.controllers;


import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.PageNumb;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.ServiceDBJooq;
import com.test.task.developer.demo.sorting.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private  ServiceDBJooq service;

    @PostMapping("/searchEmp")
    public Page<Employee> findBySearchTermEmp(//@RequestParam("searchTerm")String searchTerm,
                                           @RequestBody PageNumb pageNumb){

        return service.findBySearchTermEmployees( //searchTerm,
                PageRequest.of(pageNumb.getPageNumb(), 20));
    }

    @PostMapping("/searchTask")
    public Page<Task> findBySearchTermTask(//@RequestParam("searchTerm")String searchTerm,
                                           @RequestBody PageNumb pageNumb){

        return service.findBySearchTermTasks( //searchTerm,
                PageRequest.of(pageNumb.getPageNumb(), 20));
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

    /**Метод по удалению сотрудника
     *
     * @param id сотрудника
     * @return Возвращает валлидацию
     */
    @PostMapping("/deleteEmp")
    public ResponseEntity<String> deleteEmp(@Valid @RequestBody int id){
        service.deleteEmployee(id);
        return ResponseEntity.ok("valid");
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
