package com.test.task.developer.demo.dao.employees;

import com.test.task.developer.demo.entity.Employee;

import java.util.List;

public interface EmployeesRepository {

    List<Employee> allEmployees();
    Employee getEmployeeById(Integer id);
    void setEmployee(Employee employee);
    void deleteEmployee(Integer id_employee);
    void deleteAllEmployees();
    void updateEmployee(Employee employee);
}
