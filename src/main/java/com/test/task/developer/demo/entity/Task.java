package com.test.task.developer.demo.entity;

import java.util.Comparator;

public class Task implements Comparable<Task> {

    private Integer id;
    private Integer priority;
    private String description;
    private Integer employeeId;

//    public Task(Integer priority, String description, Integer employeeId) {
//        this.priority = priority;
//        this.description = description;
//        this.employeeId = employeeId;
//    }

    public Task(Integer id, Integer priority, String description, Integer employeeId) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.employeeId = employeeId;
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


class PriorityTaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getPriority().compareTo(o2.getPriority());
    }
}

class DescriptionTaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getDescription().compareTo(o2.getDescription());
    }
}

class EmployeeIdTaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getEmployeeId().compareTo(o2.getEmployeeId());
    }
}