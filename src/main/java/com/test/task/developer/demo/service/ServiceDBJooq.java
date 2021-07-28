package com.test.task.developer.demo.service;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceDBJooq {

    List<Employee> allEmployees();
    Employee getEmployeeById(Integer id);
    void setEmployee(Employee employee);
    void deleteEmployee(Integer id_employee);
    void deleteAllEmployees();
    void updateEmployee(Employee employee);
    Page<Employee> findBySearchTermEmployees( //String searchTerm,
                                              Pageable pageable);
    Page<Task> findBySearchTermTasks( Pageable pageable);
    List<Task> allTasks();
    Task getTasksById(Integer id);
    void setTask(Task task);
    void deleteTask(Integer id_employee);
    void deleteAllTasks();
    void updateTask(Task task);
    Integer getCountTasksByEmployeeId(Integer employeeId);
}
