package com.test.task.developer.demo.service;


import com.test.task.developer.demo.dao.employees.EmployeesRepository;
import com.test.task.developer.demo.dao.tasks.TasksRepository;
import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.PageNumb;
import com.test.task.developer.demo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Service
@Validated
public class ServiceDBJooqImpl
        implements ServiceDBJooq {

    private final EmployeesRepository employeesRepository;
    private final TasksRepository tasksRepository;

    public ServiceDBJooqImpl(EmployeesRepository employeesRepository, TasksRepository tasksRepository) {
        this.employeesRepository = employeesRepository;
        this.tasksRepository = tasksRepository;
    }


    @Override
    public List<Employee> allEmployees() {
        List<Employee> employees = employeesRepository.allEmployees();
        for (Employee employee : employees) {
            employee.setNumberTasks(
                    tasksRepository.getCountTasksByEmployeeId(employee.getId()));
        }
        return employees;
    }

    @Override
    public Page<Employee> findBySearchTermEmployees( //String searchTerm,
                                                     Pageable pageable) {

        return employeesRepository.findBySearchTerm( //searchTerm,
                pageable);
    }

    @Override
    public Page<Task> findBySearchTermTasks(Pageable pageable) {
        return tasksRepository.findBySearchTerm(pageable);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeesRepository.getEmployeeById(id);
    }

    @Override
    public void setEmployee(Employee employee) {
        employeesRepository.setEmployee(employee);
    }


    /**
     * Метод удаление сотрудника
     *
     * @param id_employee id сотрудника передаваемый из Фронта
     */
    @Override
    public void deleteEmployee(Integer id_employee) {
        employeesRepository.deleteEmployee(id_employee);
    }

    @Override
    public Integer countEmployeeHasSubordinate(Integer employeeId) {
        return employeesRepository.countEmployeeHasSubordinate(employeeId);
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

    @Override
    public Page<Employee> getEmployeesOfSorting(Map<String, Integer> map, PageNumb pageNumb) {
        if (map.get("empOfTask") == 1) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() >= 2) {
                    switch (entry.getKey()) {
                        case "empId":
                            return employeesRepository.sortingEmpId(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "empFullName":
                            return employeesRepository.sortingEmpFullName(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "empLeader":
                            return employeesRepository.sortingEmpLeader(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "empBranch":
                            return employeesRepository.sortingEmpBranch(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "empAmountTasks":
                            return employeesRepository.sortingEmpAmountTasks(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        default:
                            return employeesRepository.findBySearchTerm(PageRequest.of(pageNumb.getPageNumb(), 20));
                    }
                }

            }
        }
        return employeesRepository.findBySearchTerm(PageRequest.of(pageNumb.getPageNumb(), 20));
    }

    @Override
    public Page<Task> getTaskOfSorting(Map<String, Integer> map, PageNumb pageNumb) {
        if (map.get("empOfTask") == 0) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() >= 2) {
                    switch (entry.getKey()) {
                        case "taskId":
                            return tasksRepository.sortingTaskId(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "taskEmpId":
                            return tasksRepository.sortingTaskEmpId(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "taskDescription":
                            return tasksRepository.sortingTaskDescription(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        case "taskPriority":
                            return tasksRepository.sortingTaskPriority(entry.getValue(), PageRequest.of(pageNumb.getPageNumb(), 20));
                        default:
                            return tasksRepository.findBySearchTerm(PageRequest.of(pageNumb.getPageNumb(), 20));
                    }
                }
            }
        }
        return tasksRepository.findBySearchTerm(PageRequest.of(pageNumb.getPageNumb(),20));
    }

}



