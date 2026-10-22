package fr.isen.tamagochi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.isen.tamagochi.service.PetIsDeadException;
import fr.isen.tamagochi.service.PetNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PetNotFoundException.class)
    ProblemDetail handleNotFound(PetNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(PetIsDeadException.class)
    ProblemDetail handleDead(PetIsDeadException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }
}
