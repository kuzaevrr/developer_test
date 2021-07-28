package com.test.task.developer.demo.dao.employees;

import com.test.task.developer.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeesRepository {

    List<Employee> allEmployees();
    Employee getEmployeeById(Integer id);
    void setEmployee(Employee employee);
    void deleteEmployee(Integer id_employee);
    void deleteAllEmployees();
    void updateEmployee(Employee employee);

    Page<Employee> findBySearchTerm( //String searchTerm,
                                    Pageable pageable);
}
