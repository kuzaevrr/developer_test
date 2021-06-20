package com.test.task.developer.demo.entity;

public class Task {

    Integer id;
    Integer priority;
    String description;
    Integer employee_id;

    String nameEmployee;

    public Task(Integer priority, String description, Integer employee_id) {
        this.priority = priority;
        this.description = description;
        this.employee_id = employee_id;
    }

    public Task(Integer id, Integer priority, String description, Integer employee_id) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.employee_id = employee_id;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
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

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", employee_id=" + employee_id +
                '}';
    }
}
