package com.test.task.developer.demo.sorting.comparators.employees;

import com.test.task.developer.demo.entity.Employee;

import java.util.Comparator;

public class LeaderEmployeeComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getLeader().compareTo(o2.getLeader());
    }
}