package web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.dto.DtoService;
import web.dto.response.ResponseMessage;
import web.exception.DataException;
import web.validation.error.ValidationError;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerController{
  private final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);

  @ExceptionHandler(DataException.class)
  public ResponseEntity<ResponseMessage> notFound(DataException exception) {
    log.warn(exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DtoService.responseMessage(exception.getMessage()));
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ResponseMessage> invalidFile() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage("Неверный формат файла (.jpg)"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseMessage> exception(Exception ex) {
    log.warn(String.valueOf(ex));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DtoService.responseMessage("Ошибка сервера"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseMessage> validation(MethodArgumentNotValidException ex) {
    ValidationError validationError = new ValidationError();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String errorMessage = error.getDefaultMessage();
      validationError.setMessage(errorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage(validationError.getMessage()));
  }
}