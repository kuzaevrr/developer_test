package com.test.task.developer.demo.controllers;

import com.test.task.developer.demo.exception_handling.EmployeeIncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;


@ControllerAdvice
public class ControllerExceptionHandler {

//PSQLException

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handlerException(SQLException exception){
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.LOCKED);
    }
}
