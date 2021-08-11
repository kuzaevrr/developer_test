package com.test.task.developer.demo.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Comparator;

public class Task implements Comparable<Task> {

    private Integer id;
    @Min(1)
    @Max(9)
    private Integer priority;
    private String description;
    private Integer employeeId;

    private String employeeFullName;

    public Task(Integer id, Integer priority, String description, Integer employeeId) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.employeeId = employeeId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employee_id) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + this.id +
                ", priority=" + this.priority +
                ", description='" + this.description + '\'' +
                ", employee_id=" + this.employeeId +
                '}';
    }

    @Override
    public int compareTo(Task task) {
        return this.id.compareTo(task.id);
    }
}





