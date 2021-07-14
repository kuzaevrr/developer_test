package com.test.task.developer.demo.dao.tasks;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;

import java.util.List;

public interface TasksRepository {

    List<Task> allTasks();
    Task getTasksById(Integer id);
    void setTask(Task task);
    void deleteTask(Integer id_employee);
    void deleteAllTasks();
    void updateTask(Task task);
}
