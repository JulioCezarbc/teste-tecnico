package com.julio.tgid.controller;

import com.julio.tgid.exception.AlreadyInUse;
import com.julio.tgid.exception.ErrorMessage;
import com.julio.tgid.exception.ObjectNotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice

public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<ErrorMessage> objectNotFoundException(ObjectNotFound e){
        ErrorMessage errorMsg = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
    }
    @ExceptionHandler(AlreadyInUse.class)
    public ResponseEntity<ErrorMessage> alreadyInUseException(AlreadyInUse e){
        ErrorMessage errorMsg = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMsg);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String errorMessage;

        if (ex.getMessage() != null && ex.getMessage().contains("unique")) {
            errorMessage = "Unique constraint violation: " + ex.getMessage();
        } else if (ex.getMessage() != null && ex.getMessage().contains("not-null")) {
            errorMessage = "Not null constraint violation: " + ex.getMessage();
        } else {
            errorMessage = "Data integrity issue: " + ex.getMessage();
        }

        ErrorMessage errorMsg = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String errorMessage = "Data integrity violation occurred";
        if (ex.getRootCause() != null) {
            errorMessage = ex.getRootCause().getMessage();
        }
        ErrorMessage errorMsg = new ErrorMessage(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

}
