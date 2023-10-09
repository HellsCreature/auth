package com.ars.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Arseniy: Шаблон для обработки ошибок
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public String handleException(Exception exception) { //
    return "Sorry, something went wrong: " + exception.getMessage();
  }

}
