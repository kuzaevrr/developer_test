package com.test.task.developer.demo.sorting.comparators.tasks;

import com.test.task.developer.demo.entity.Task;

import java.util.Comparator;

public class PriorityTaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getPriority().compareTo(o2.getPriority());
    }
}