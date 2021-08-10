package com.test.task.developer.demo.dao.employees;

import com.test.task.developer.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeesRepository {

    List<Employee> allEmployees();
    Employee getEmployeeById(Integer id);
    void setEmployee(Employee employee);
    void deleteEmployee(Integer employeeId);
    void deleteAllEmployees();
    void updateEmployee(Employee employee);
    Integer countEmployeeHasSubordinate(Integer employeeId);
    Page<Employee> findBySearchTerm( //String searchTerm,
                                    Pageable pageable);


    Page<Employee> sortingEmpId(int asc, Pageable pageable);
    Page<Employee> sortingEmpFullName(int asc, Pageable pageable);
    Page<Employee> sortingEmpLeader(int asc, Pageable pageable);
    Page<Employee> sortingEmpBranch(int asc, Pageable pageable);
    Page<Employee> sortingEmpСountTasks(int asc, Pageable pageable);

}
