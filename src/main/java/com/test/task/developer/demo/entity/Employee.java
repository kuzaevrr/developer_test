package com.test.task.developer.demo.entity;

public class Employee {

    Integer id;
    String full_name;
    Integer leader;
    String branch_name;

    Integer number_tasks;
    String leaderName;

    public Employee(String full_name, Integer leader,  String branch_name) {
        this.full_name = full_name;
        this.leader = leader;
        this.branch_name = branch_name;
    }

    public Employee(Integer id, String full_name, Integer leader, String branch_name) {
        this.id = id;
        this.full_name = full_name;
        this.leader = leader;
        this.branch_name = branch_name;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public Integer getNumber_tasks() {
        return number_tasks;
    }

    public void setNumber_tasks(Integer number_tasks) {
        this.number_tasks = number_tasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }


    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public Employee() {

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", leader=" + leader +
                ", branch_name='" + branch_name + '\'' +
                '}';
    }
}
