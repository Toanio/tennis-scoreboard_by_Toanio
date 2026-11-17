package com.example.tennis.scoreboard.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageDto> handleWrongArgument(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(400).body(new MessageDto("Invalid UUID"));
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<MessageDto> handleNotFoundMatch(MatchNotFoundException ex) {
        return ResponseEntity.status(404).body(new MessageDto(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> handleNotValidArgument(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .filter(error -> error.getCode().equals("NotBlank") || error.getCode().equals("NotNull"))
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElseGet(() -> fieldErrors.get(0).getDefaultMessage());
        return ResponseEntity.status(400).body(new MessageDto(errorMessage));
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<MessageDto> handleNotFoundMatch(PlayerAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(new MessageDto(ex.getMessage()));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<MessageDto> handleNotFoundMatch(PlayerNotFoundException ex) {
        return ResponseEntity.status(404).body(new MessageDto(ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MessageDto> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();

        return ResponseEntity.status(400)
                .body(new MessageDto(errorMessage));
    }

}
