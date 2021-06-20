package com.test.task.developer.demo.service;

import com.test.task.developer.demo.dao.employees.EmployeesRepository;
import com.test.task.developer.demo.dao.tasks.TasksRepository;
import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public List<Employee> allEmployees() {
        return employeesRepository.allEmployees();
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
        return tasksRepository.allTasks();
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
}
