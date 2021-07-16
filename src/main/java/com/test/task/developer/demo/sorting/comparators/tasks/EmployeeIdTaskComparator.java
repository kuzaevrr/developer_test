package com.test.task.developer.demo.sorting.comparators.tasks;

import com.test.task.developer.demo.entity.Task;

import java.util.Comparator;

public class EmployeeIdTaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getEmployeeId().compareTo(o2.getEmployeeId());
    }
}