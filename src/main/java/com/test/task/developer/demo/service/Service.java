package com.test.task.developer.demo.service;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;

import java.util.List;

public interface Service {

    List<Employee> allEmployees();
    Employee getEmployeeById(Integer id);
    void setEmployee(Employee employee);
    void deleteEmployee(Integer id_employee);
    void deleteAllEmployees();

    List<Task> allTasks();
    Task getTasksById(Integer id);
    void setTask(Task task);
    void deleteTask(Integer id_employee);
    void deleteAllTasks();
}
