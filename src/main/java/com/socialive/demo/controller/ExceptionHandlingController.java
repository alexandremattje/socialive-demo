package com.socialive.demo.controller;

import com.socialive.demo.exception.RecordAlreadyExistsException;
import com.socialive.demo.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

  @ResponseStatus(value= HttpStatus.CONFLICT, reason="Data integrity violation")
  @ExceptionHandler(RecordAlreadyExistsException.class)
  public void conflict() {
  }

  @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Record not found")
  @ExceptionHandler(RecordNotFoundException.class)
  public void notFound() {
  }
}