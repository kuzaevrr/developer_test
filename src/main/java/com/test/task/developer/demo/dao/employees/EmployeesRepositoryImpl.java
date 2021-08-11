package com.test.task.developer.demo.dao.employees;


import com.jooq.postgress.project.jooq_postgress_project.tables.Employees;
import com.jooq.postgress.project.jooq_postgress_project.tables.Tasks;
import com.jooq.postgress.project.jooq_postgress_project.tables.records.EmployeesRecord;
import com.test.task.developer.demo.dao.tasks.TasksRepository;
import com.test.task.developer.demo.entity.Employee;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.lang.reflect.Field;

import static com.jooq.postgress.project.jooq_postgress_project.tables.Employees.*;
import static org.jooq.impl.DSL.*;

@Repository
@Transactional
public class EmployeesRepositoryImpl
        implements EmployeesRepository {

    private final DSLContext dsl;

    public EmployeesRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Page<Employee> sortingEmpId(int asc, Pageable pageable) {
        List<EmployeesRecord> queryResults;
        if (asc == 2) {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.ID.asc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        } else {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.ID.desc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        }
        return new PageImpl<>(convertQueryResultsToModelObjects(queryResults), pageable, findCountByLikeExpression());
    }

    @Override
    public Page<Employee> sortingEmpFullName(int asc, Pageable pageable) {
        List<EmployeesRecord> queryResults;
        if (asc == 2) {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.FULL_NAME.asc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        } else {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.FULL_NAME.desc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        }
        return new PageImpl<>(convertQueryResultsToModelObjects(queryResults), pageable, findCountByLikeExpression());
    }

    @Override
    public Page<Employee> sortingEmpLeader(int asc, Pageable pageable) {
        List<EmployeesRecord> queryResults;
        if (asc == 2) {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.LEADER.asc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        } else {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.LEADER.desc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        }
        return new PageImpl<>(convertQueryResultsToModelObjects(queryResults), pageable, findCountByLikeExpression());
    }

    @Override
    public Page<Employee> sortingEmpBranch(int asc, Pageable pageable) {
        List<EmployeesRecord> queryResults;
        if (asc == 2) {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.BRANCH_NAME.asc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        } else {
            queryResults = dsl.selectFrom(EMPLOYEES)
                    .orderBy(EMPLOYEES.BRANCH_NAME.desc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        }
        return new PageImpl<>(convertQueryResultsToModelObjects(queryResults), pageable, findCountByLikeExpression());
    }

    @Override
    public Page<Employee> sortingEmpAmountTasks(int asc, Pageable pageable) {
        List<EmployeesRecord> queryResults;
        if (asc == 2) {
//            queryResults = dsl.selectFrom(EMPLOYEES)
//                    .orderBy(Tasks.TASKS.EMPLOYEE_ID.asc())
//                    .limit(pageable.getPageSize())
//                    .offset(pageable.getOffset())
//                    .fetchInto(EmployeesRecord.class);
            queryResults = dsl.select(
                    asterisk(),
                    field(select(count())
                            .from(Tasks.TASKS)
                            .where(Tasks.TASKS.EMPLOYEE_ID.eq(Employees.EMPLOYEES.ID))).as(Tasks.TASKS.EMPLOYEE_ID))
                    .from(Employees.EMPLOYEES)
                    .orderBy(Employees.EMPLOYEES.ID.desc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        } else {
//            queryResults = dsl.selectFrom(EMPLOYEES)
//                    .orderBy(Tasks.TASKS.EMPLOYEE_ID.desc())
//                    .limit(pageable.getPageSize())
//                    .offset(pageable.getOffset())
//                    .fetchInto(EmployeesRecord.class);
            queryResults = dsl.select(
                    asterisk(),
                    field(select(count())
                            .from(Tasks.TASKS)
                            .where(Tasks.TASKS.EMPLOYEE_ID.eq(Employees.EMPLOYEES.ID))).as(Tasks.TASKS.EMPLOYEE_ID))
                    .from(Employees.EMPLOYEES)
                    .orderBy(Employees.EMPLOYEES.ID.asc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(EmployeesRecord.class);
        }
        return new PageImpl<>(convertQueryResultsToModelObjects(queryResults), pageable, findCountByLikeExpression());
    }


    @Override
    public Page<Employee> findBySearchTerm(Pageable pageable) {

        List<EmployeesRecord> queryResults = dsl.selectFrom(EMPLOYEES)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(EmployeesRecord.class);

        List<Employee> todoEntries = convertQueryResultsToModelObjects(queryResults);
        long totalCount = findCountByLikeExpression();
        return new PageImpl<>(todoEntries, pageable, totalCount);
    }

    //количество запросов
    private long findCountByLikeExpression( //String likeExpression
    ) {
        return dsl.fetchCount(dsl.select()
                        .from(EMPLOYEES)
//                .where(Employees.EMPLOYEES.BRANCH_NAME.likeIgnoreCase(likeExpression))
        );
    }

    private Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();

        if (sortSpecification == null) {
            return querySortFields;
        }
        //
        Iterator<Sort.Order> specifiedFields = sortSpecification.iterator();
        while (specifiedFields.hasNext()) {
            Sort.Order specifiedField = specifiedFields.next();

            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();

            TableField tableField = getTableField(sortFieldName);
            SortField<?> querySortField = convertTableFieldToSortField(tableField, sortDirection);
            querySortFields.add(querySortField);
        }
        return querySortFields;
    }

    private TableField getTableField(String sortFieldName) {
        TableField sortField = null;
        try {
            Field tableField = EMPLOYEES.getClass().getField(sortFieldName);
            sortField = (TableField) tableField.get(EMPLOYEES);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            String errorMessage = String.format("Could not find table field: {}", sortFieldName);
            throw new InvalidDataAccessApiUsageException(errorMessage, ex);
        }

        return sortField;
    }

    private SortField<?> convertTableFieldToSortField(TableField tableField, Sort.Direction sortDirection) {
        if (sortDirection == Sort.Direction.ASC) {
            return tableField.asc();
        } else {
            return tableField.desc();
        }
    }

    private List<Employee> convertQueryResultsToModelObjects(List<EmployeesRecord> queryResults) {
        List<Employee> employeeEntry = new ArrayList<>();

        for (EmployeesRecord queryResult : queryResults) {
            Employee employee = getEmployeeEntity(queryResult);
            employee.setNumberTasks(
                    getCountTask(employee.getId()));
            if (employee.getLeader() != null) {
                employee.setLeaderName(
                        getEmployeeById(employee.getLeader())
                                .getFullName());
            }
            employeeEntry.add(employee);
        }
        return employeeEntry;
    }

    private Integer getCountTask(int employeeId) {
        return dsl.selectCount()
                .from(Tasks.TASKS)
                .where(Tasks.TASKS.EMPLOYEE_ID.eq(employeeId))
                .fetchAny(0, Integer.class);
    }

    @Override
    public List<Employee> allEmployees() {
        List<Employee> posts = new ArrayList<>();
        Result<Record> result = dsl
                .select()
                .from(EMPLOYEES)
                .fetch();
        for (Record r : result) {
            posts.add(getEmployeeEntity(r));
        }
        return posts;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return Objects.requireNonNull(dsl.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(id))
                .fetchAny())
                .into(Employee.class);
    }


    @Override
    public void setEmployee(Employee employee) {

        dsl.insertInto(EMPLOYEES)
                .set(EMPLOYEES.FULL_NAME, employee.getFullName())
                .set(EMPLOYEES.LEADER, employee.getLeader())
                .set(EMPLOYEES.BRANCH_NAME, employee.getBranchName())
                .returning(EMPLOYEES.ID)
                .fetchOne();
    }

    @Override
    public void updateEmployee(Employee employee) {
        dsl.update(EMPLOYEES)
                .set(dsl.newRecord(EMPLOYEES, employee))
                .where(EMPLOYEES.ID.eq(employee.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + employee.getId()))
                .into(Employee.class);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        dsl.deleteFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(employeeId))
                .execute();
    }


    @Override
    public Integer countEmployeeHasSubordinate(Integer employeeId) {
        return dsl.selectCount()
                .from(EMPLOYEES)
                .where(EMPLOYEES.LEADER.eq(employeeId))
                .fetchAny(0, Integer.class);
    }

    @Override
    public void deleteAllEmployees() {
        dsl.truncate(EMPLOYEES)
                .execute();
    }

    private Employee getEmployeeEntity(Record r) {
        Integer id = r.getValue(EMPLOYEES.ID, Integer.class);
        String full_name = r.getValue(EMPLOYEES.FULL_NAME, String.class);
        Integer leader = r.getValue(EMPLOYEES.LEADER, Integer.class);
        String branch_name = r.getValue(EMPLOYEES.BRANCH_NAME, String.class);

        return new Employee(id, full_name, leader, branch_name);
    }
}
