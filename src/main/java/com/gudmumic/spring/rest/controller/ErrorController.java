package com.gudmumic.spring.rest.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException e) {

        List errors = e.getFieldErrors().stream()
                .map(objectError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(objectError.getField(), objectError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolationException(TransactionSystemException e) {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
        if (!(e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException)) {
            ConstraintViolationException cve = (ConstraintViolationException) e.getCause().getCause();

            List errors = cve.getConstraintViolations().stream()
                    .map(violation -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
                        return errorMap;
                    }).collect(Collectors.toList());
            return responseEntity.body(errors);
        }
        return responseEntity.build();
    }
}
