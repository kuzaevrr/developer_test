package com.test.task.developer.demo;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DeveloperTestApplicationTests {

    @Autowired
    private Service service;

    @Test
    void contextLoad() {

    }

    @Test
    void setEmployees() {
        Employee employee = new Employee();
        employee.setFull_name("Максимов Максим Максимович");
//        employee.setLeader(15);
        employee.setBranch_name("ДОТС");
        service.setEmployee(employee);
        System.out.println(employee);
    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = service.allEmployees();
        for(Employee employee: employees){
            System.out.println(employee.getId());
        }
    }

    @Test
    void getEmployees() {
        Employee employee = service.getEmployeeById(15);
        System.out.println(employee);
    }

    @Test
    void getAllTask(){
        List<Task> tasks = service.allTasks();
        for(Task task: tasks){
            System.out.println(task.getId());
        }
    }

    @Test
    void getTask (){
        Task task = service.getTasksById(15);
        System.out.println(task);
    }

    @Test
    void setTask(){
        service.setTask(new Task(
                7,
                "Прогулка",
                15
        ));
    }
}
