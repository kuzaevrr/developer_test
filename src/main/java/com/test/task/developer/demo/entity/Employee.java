package com.test.task.developer.demo.entity;

import java.util.Comparator;

public class Employee implements Comparable<Employee> {

    private Integer id;
    private String fullName;
    private Integer leader;
    private String branchName;
    private Integer numberTasks;


    public Employee(Integer id, String fullName, Integer leader, String branchName) {
        this.id = id;
        this.fullName = fullName;
        this.leader = leader;
        this.branchName = branchName;
    }


    public Integer getNumberTasks() {
        return numberTasks;
    }

    public void setNumberTasks(Integer numberTasks) {
        this.numberTasks = numberTasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Employee() {

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + this.id +
                ", full_name='" + this.fullName + '\'' +
                ", leader=" + this.leader +
                ", branch_name='" + this.branchName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Employee employee) {
        return this.id.compareTo(employee.id);
    }
}







