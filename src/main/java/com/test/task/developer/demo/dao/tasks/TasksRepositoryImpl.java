package com.test.task.developer.demo.dao.tasks;

import com.jooq.postgress.project.jooq_postgress_project.tables.Employees;
import com.jooq.postgress.project.jooq_postgress_project.tables.Tasks;
import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
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
public class TasksRepositoryImpl
        implements TasksRepository {

    @Autowired
    private DSLContext dsl;


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
    public Task getTasksById(Integer id) {
        return Objects.requireNonNull(dsl.selectFrom(Tasks.TASKS)
                .where(Tasks.TASKS.ID.eq(id))
                .fetchAny())
                .into(Task.class);
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
