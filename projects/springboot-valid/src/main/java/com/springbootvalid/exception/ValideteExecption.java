package com.springbootvalid.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValideteExecption extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> msg=new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(e -> {
                FieldError error = (FieldError) e;
                msg.put(error.getField(), error.getDefaultMessage());
            });
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}
