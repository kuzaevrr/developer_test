package com.test.task.developer.demo.dao.tasks;

import com.jooq.postgress.project.jooq_postgress_project.tables.Employees;
import com.jooq.postgress.project.jooq_postgress_project.tables.Tasks;
import com.jooq.postgress.project.jooq_postgress_project.tables.records.EmployeesRecord;
import com.jooq.postgress.project.jooq_postgress_project.tables.records.TasksRecord;
import com.test.task.developer.demo.dao.employees.EmployeesRepository;
import com.test.task.developer.demo.dao.employees.EmployeesRepositoryImpl;
import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
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

import java.lang.reflect.Field;
import java.util.*;


@Repository
@Transactional
public class TasksRepositoryImpl
        implements TasksRepository {

    private final DSLContext dsl;

    public TasksRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<Task> allTasks() {
        List<Task> posts = new ArrayList<>();
        Result<Record> result = dsl.select().from(Tasks.TASKS).fetch();
        for (Record r : result) {
            posts.add(getTaskEntity(r));
        }
        return posts;
    }


    @Override
    public Page<Task> findBySearchTerm( //String searchTerm,
                                        Pageable pageable) {

        //String likeExpression = "%" + searchTerm + "%";
//        System.out.println(pageable.getPageNumber());
        List<TasksRecord> queryResults = dsl.selectFrom(Tasks.TASKS)
//                .where(Employees.EMPLOYEES.BRANCH_NAME.likeIgnoreCase(likeExpression))
//                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(TasksRecord.class);

        List<Task> todoEntries = convertQueryResultsToModelObjects(queryResults);
        long totalCount = findCountByLikeExpression();
        return new PageImpl<>(todoEntries, pageable, totalCount);
    }

    //количество запросов
    private long findCountByLikeExpression( //String likeExpression
    ) {
        return dsl.fetchCount(dsl.select()
                        .from(Tasks.TASKS)
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
            Field tableField = Tasks.TASKS.getClass().getField(sortFieldName);
            sortField = (TableField) tableField.get(Tasks.TASKS);
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

    private List<Task> convertQueryResultsToModelObjects(List<TasksRecord> queryResults) {
        List<Task> employeeEntry = new ArrayList<>();

        for (TasksRecord queryResult : queryResults) {
            Task task = getTaskEntity(queryResult);
            task.setEmployeeFullName(
                    Objects.requireNonNull(dsl.selectFrom(Employees.EMPLOYEES)
                            .where(Employees.EMPLOYEES.ID.eq(task.getEmployeeId()))
                            .fetchAny())
                            .into(Employee.class)
                            .getFullName());
            employeeEntry.add(task);
        }
        return employeeEntry;
    }

    @Override
    public Task getTasksById(Integer id) {
        return Objects.requireNonNull(dsl.selectFrom(Tasks.TASKS)
                .where(Tasks.TASKS.ID.eq(id))
                .fetchAny())
                .into(Task.class);
    }

    @Override
    public Integer getCountTasksByEmployeeId(Integer employeeId) {
        return dsl.selectCount()
                .from(Tasks.TASKS)
                .where(Tasks.TASKS.EMPLOYEE_ID.eq(employeeId))
                .fetchAny(0, Integer.class);
    }

    @Override
    public void setTask(Task task) {
        dsl.insertInto(Tasks.TASKS)
                .set(Tasks.TASKS.PRIORITY, task.getPriority())
                .set(Tasks.TASKS.DESCRIPTION, task.getDescription())
                .set(Tasks.TASKS.EMPLOYEE_ID, task.getEmployeeId())
                .returning(Tasks.TASKS.ID)
                .fetchOne();
    }

    @Override
    public void deleteTask(Integer id_tasks) {
        dsl.deleteFrom(Tasks.TASKS)
                .where(Tasks.TASKS.ID.eq(id_tasks))
                .execute();
    }

    @Override
    public void deleteAllTasks() {
        dsl.truncate(Tasks.TASKS)
                .execute();
    }

    @Override
    public void updateTask(Task task) {
        dsl.update(Tasks.TASKS)
                .set(dsl.newRecord(Tasks.TASKS, task))
                .where(Tasks.TASKS.ID.eq(task.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + task.getId()))
                .into(Task.class);
    }

    private Task getTaskEntity(Record r) {
        Integer id = r.getValue(Tasks.TASKS.ID, Integer.class);
        Integer priority = r.getValue(Tasks.TASKS.PRIORITY, Integer.class);
        String description = r.getValue(Tasks.TASKS.DESCRIPTION, String.class);
        Integer employee_id = r.getValue(Tasks.TASKS.EMPLOYEE_ID, Integer.class);
        return new Task(id, priority, description, employee_id);
    }


}
