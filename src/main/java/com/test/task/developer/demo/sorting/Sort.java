package com.test.task.developer.demo.sorting;

import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.sorting.comparators.employees.BranchNameEmployeeComparator;
import com.test.task.developer.demo.sorting.comparators.employees.LeaderEmployeeComparator;
import com.test.task.developer.demo.sorting.comparators.employees.NameEmployeeComparator;
import com.test.task.developer.demo.sorting.comparators.employees.NumberTaskEmployeeComparator;
import com.test.task.developer.demo.sorting.comparators.tasks.DescriptionTaskComparator;
import com.test.task.developer.demo.sorting.comparators.tasks.EmployeeIdTaskComparator;
import com.test.task.developer.demo.sorting.comparators.tasks.PriorityTaskComparator;

import java.util.*;


public class Sort<T> {

    private List<T> list;


    public Sort() {
    }

    public Sort(List<T> list) {
        this.list = list;
    }

    public List<T> sortList(Map<String, Integer> map) {
        List<T> listRes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 2) {
                listRes = result(key);
                break;
            } else if (value == 3) {
                List<T> listO = result(key);
                Collections.reverse(listO);
                listRes = listO;
                break;
            }
        }
        return listRes;
    }

    private List<T> result(String key) {
        switch (key) {
            case "empId":
                Collections.sort((List<Employee>) list);
                return list;
            case "empFullName":
                Collections.sort((List<Employee>) list, new NameEmployeeComparator());
                return list;
            case "empLeader":
                List<Employee> empls = new ArrayList<>();
                for (Employee employee: (List<Employee>)list){
                    if(employee.getLeader() == null){
                        employee.setLeader(0);
                    }
                    empls.add(employee);
                }
                Collections.sort(empls, new LeaderEmployeeComparator());
                return list;
            case "empBranch":
                Collections.sort((List<Employee>) list, new BranchNameEmployeeComparator());
                return list;
            case "empСountTasks":
                Collections.sort((List<Employee>) list, new NumberTaskEmployeeComparator());
                return list;
            case "taskId":
                Collections.sort((List<Task>) list);
                return list;
            case "taskDescription":
                Collections.sort((List<Task>) list, new DescriptionTaskComparator());
                return list;
            case "taskEmpId":
                Collections.sort((List<Task>) list, new EmployeeIdTaskComparator());
                return list;
            case "taskPriority":
                Collections.sort((List<Task>) list, new PriorityTaskComparator());
                return list;
            default:
                return new ArrayList<>();
        }
    }
}
