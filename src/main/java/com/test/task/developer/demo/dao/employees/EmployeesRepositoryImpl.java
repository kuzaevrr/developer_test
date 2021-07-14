package com.test.task.developer.demo.dao.employees;


import com.jooq.postgress.project.jooq_postgress_project.tables.Employees;
import com.test.task.developer.demo.entity.Employee;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class EmployeesRepositoryImpl
        implements EmployeesRepository
{


    @Autowired
    private DSLContext dsl;

    @Override
    public List<Employee> allEmployees() {
        List<Employee> posts = new ArrayList<>();
        Result<Record> result = dsl.select().from(Employees.EMPLOYEES).fetch();
        for (Record r : result) {
            posts.add(getEmployeeEntity(r));
        }
        return posts;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return Objects.requireNonNull(dsl.selectFrom(Employees.EMPLOYEES)
                .where(Employees.EMPLOYEES.ID.eq(id))
                .fetchAny())
                .into(Employee.class);
    }

    @Override
    public void setEmployee(Employee employee) {

        dsl.insertInto(Employees.EMPLOYEES)
                .set(Employees.EMPLOYEES.FULL_NAME, employee.getFullName())
                .set(Employees.EMPLOYEES.LEADER, employee.getLeader())
                .set(Employees.EMPLOYEES.BRANCH_NAME, employee.getBranchName())
                .returning(Employees.EMPLOYEES.ID)
                .fetchOne();
    }

    @Override
    public void updateEmployee(Employee employee){
        dsl.update(Employees.EMPLOYEES)
                .set(dsl.newRecord(Employees.EMPLOYEES, employee))
                .where(Employees.EMPLOYEES.ID.eq(employee.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + employee.getId()))
                .into(Employee.class);
    }

    @Override
    public void deleteEmployee(Integer id_employee) {
        dsl.deleteFrom(Employees.EMPLOYEES)
                .where(Employees.EMPLOYEES.ID.eq(id_employee))
                .execute();
    }

    @Override
    public void deleteAllEmployees() {
        dsl.truncate(Employees.EMPLOYEES)
                .execute();
    }

    private Employee getEmployeeEntity(Record r){
        Integer id = r.getValue(Employees.EMPLOYEES.ID, Integer.class);
        String full_name = r.getValue(Employees.EMPLOYEES.FULL_NAME, String.class) ;
        Integer leader = r.getValue(Employees.EMPLOYEES.LEADER, Integer.class) ;
        String branch_name = r.getValue(Employees.EMPLOYEES.BRANCH_NAME, String.class);

        return new Employee(id, full_name, leader, branch_name);
    }
}
