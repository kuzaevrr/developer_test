package com.test.task.developer.demo.dao.tasks;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TasksRepository {

    List<Task> allTasks();
    Task getTasksById(Integer id);
    void setTask(Task task);
    void deleteTask(Integer id_employee);
    void deleteAllTasks();
    void updateTask(Task task);
    Integer getCountTasksByEmployeeId(Integer employeeId);
    Page<Task> findBySearchTerm( Pageable pageable);
}
