package com.test.task.developer.demo.service;

import com.test.task.developer.demo.dao.employees.EmployeesRepository;
import com.test.task.developer.demo.dao.tasks.TasksRepository;
import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.sorting.comparators.tasks.PriorityTaskComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ServiceDBJooqImpl
        implements ServiceDBJooq {

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public List<Employee> allEmployees() {
        List<Employee> employees = employeesRepository.allEmployees();
        for (Employee employee : employees) {
            employee.setNumberTasks(
                    tasksRepository.getCountTasksByEmployeeId(employee.getId()));
        }
        return employees;
    }

    public Page<Employee> findBySearchTerm(String searchTerm, Pageable pageable){
        return employeesRepository.findBySearchTerm(searchTerm, pageable);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeesRepository.getEmployeeById(id);
    }

    @Override
    public void setEmployee(Employee employee) {
        employeesRepository.setEmployee(employee);
    }

    @Override
    public void deleteEmployee(Integer id_employee) {
        employeesRepository.deleteEmployee(id_employee);
    }

    @Override
    public void deleteAllEmployees() {
        employeesRepository.deleteAllEmployees();
    }

    @Override
    public List<Task> allTasks() {
        List<Task> tasks = tasksRepository.allTasks();
        Collections.sort(tasks, new PriorityTaskComparator());
        return tasks;
    }

    @Override
    public Task getTasksById(Integer id) {
        return tasksRepository.getTasksById(id);
    }

    @Override
    public void setTask(Task task) {
        tasksRepository.setTask(task);
    }

    @Override
    public void deleteTask(Integer id_employee) {
        tasksRepository.deleteTask(id_employee);
    }

    @Override
    public void deleteAllTasks() {
        tasksRepository.deleteAllTasks();
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeesRepository.updateEmployee(employee);
    }

    @Override
    public void updateTask(Task task) {
        tasksRepository.updateTask(task);
    }

    @Override
    public Integer getCountTasksByEmployeeId(Integer employeeId) {
        return tasksRepository.getCountTasksByEmployeeId(employeeId);
    }

}
