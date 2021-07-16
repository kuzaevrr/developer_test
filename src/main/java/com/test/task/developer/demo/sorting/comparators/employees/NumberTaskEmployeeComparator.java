package com.test.task.developer.demo.sorting.comparators.employees;

import com.test.task.developer.demo.entity.Employee;

import java.util.Comparator;

public class NumberTaskEmployeeComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getNumberTasks().compareTo(o2.getNumberTasks());
    }
}

