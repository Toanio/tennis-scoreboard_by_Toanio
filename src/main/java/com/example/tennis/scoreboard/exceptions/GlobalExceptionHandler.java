package com.example.tennis.scoreboard.exceptions;

import com.example.tennis.scoreboard.Players.Players;
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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MessageDto> handleNotFoundMatch(NullPointerException ex) {
        return ResponseEntity.status(404).body(new MessageDto("Match with this UUID not found"));
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

}
