package com.test.task.developer.demo.entity;

public class Task {

    Integer id;
    Integer priority;
    String description;
    Integer employeeId;

//    String nameEmployee;

    public Task(Integer priority, String description, Integer employeeId) {
        this.priority = priority;
        this.description = description;
        this.employeeId = employeeId;
    }

    public Task(Integer id, Integer priority, String description, Integer employeeId) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.employeeId = employeeId;
    }

//    public String getNameEmployee() {
//        return nameEmployee;
//    }
//
//    public void setNameEmployee(String nameEmployee) {
//        this.nameEmployee = nameEmployee;
//    }

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

    public void setEmployee_id(Integer employee_id) {
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
}
