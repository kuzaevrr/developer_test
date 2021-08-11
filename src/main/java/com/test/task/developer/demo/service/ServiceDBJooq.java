package com.test.task.developer.demo.service;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.PageNumb;
import com.test.task.developer.demo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ServiceDBJooq {

    List<Employee> allEmployees();
    Employee getEmployeeById(Integer id);
    void setEmployee(Employee employee);
    void deleteEmployee(Integer id_employee);
    void deleteAllEmployees();
    void updateEmployee(Employee employee);
    Page<Employee> findBySearchTermEmployees( //String searchTerm,
                                              Pageable pageable);
    Integer countEmployeeHasSubordinate(Integer employeeId);
    Page<Employee> getEmployeesOfSorting(Map<String, Integer> map,  PageNumb pageNumb);
    Page<Task> getTaskOfSorting (Map <String, Integer> map, PageNumb pageNumb) ;

    Page<Task> findBySearchTermTasks( Pageable pageable);
    List<Task> allTasks();
    Task getTasksById(Integer id);
    void setTask(Task task);
    void deleteTask(Integer id_employee);
    void deleteAllTasks();
    void updateTask(Task task);
    Integer getCountTasksByEmployeeId(Integer employeeId);
}
