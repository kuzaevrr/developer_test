package com.test.task.developer.demo.aop;


import com.jooq.postgress.project.jooq_postgress_project.tables.Employees;
import com.test.task.developer.demo.entity.Employee;
import com.test.task.developer.demo.entity.Task;
import com.test.task.developer.demo.service.ServiceDBJooqImpl;
import io.micrometer.core.instrument.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Aspect
public class ValidationAspect {

    @Autowired
    private ServiceDBJooqImpl service;

    @Pointcut("execution(public void com.test.task.developer.demo.service.ServiceDBJooqImpl.setTask(*))")
    private void setTask(){}

    @Pointcut("execution(public void com.test.task.developer.demo.service.ServiceDBJooqImpl.updateTask(*))")
    private void updateTask(){}
    /**
     * проверка на наличие подчиненого, на наличие незавершонной задчаи
     */
    @Before("execution(public void com.test.task.developer.demo.service.ServiceDBJooqImpl.deleteEmployee(Integer))")
    public void validationDeleteEmployee(JoinPoint joinPoint) throws SQLException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        int employeeId = Integer.parseInt(joinPoint.getArgs()[0].toString());

        if (service.countEmployeeHasSubordinate(employeeId) > 0) {
            throw new SQLException("У данного сотрудника имеется подчиненый");
        } else if (service.getCountTasksByEmployeeId(employeeId) > 0) {
            throw new SQLException("У данного сотрудника имеются не завершенные задачи");
        }
    }

    /**
     * Проверка на подчиненого
     *
     * @param joinPoint
     * @throws SQLException выброс исключения валидации
     */
    @Before("execution(public void com.test.task.developer.demo.service.ServiceDBJooqImpl.updateEmployee(*))")
    public void validationSaveEmployee(JoinPoint joinPoint) throws SQLException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if(joinPoint.getArgs() != null && joinPoint.getArgs().length != 0){
            Employee employee = (Employee) joinPoint.getArgs()[0];
            if(employee.getLeader() != null) {
                if (service.getEmployeeById(employee.getLeader()).getLeader() == employee.getId()) {
                    throw new SQLException("Нельзя сотруднику указывать, руководителя являющийся его подчиненым.");
                } else if (employee.getId() == employee.getLeader()) {
                    throw new SQLException("Нельзя указывать руководителя, самого сотрудника.");
                }
            }
        }
    }

    @Before("setTask() || updateTask()")
    public void validationSaveTask(JoinPoint joinPoint) throws SQLException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Task task = (Task) joinPoint.getArgs()[0];

        if (task.getDescription() == null || task.getPriority() == null || task.getPriority() < 1 || task.getPriority() > 9) {
            throw new SQLException("При изменении или создании задачи, необходимо заполнить все поля и приоритет от 1 до 9 включительно.");
        }

    }
}
