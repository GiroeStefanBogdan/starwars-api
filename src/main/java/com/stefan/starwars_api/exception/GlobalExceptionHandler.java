package com.stefan.starwars_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(int status, String error, String message) {}

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCharacterNotFound(CharacterNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new ErrorResponse(404, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(SwapiUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleSwapiUnavailable(SwapiUnavailableException ex) {
        return ResponseEntity.status(502)
                .body(new ErrorResponse(502, "Bad Gateway", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(400)
                .body(new ErrorResponse(400, "Bad Request", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(400)
                .body(new ErrorResponse(400, "Bad Request",
                        "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'."));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingHeader(MissingRequestHeaderException ex) {
        return ResponseEntity.status(400)
                .body(new ErrorResponse(400, "Bad Request",
                        "Required header '" + ex.getHeaderName() + "' is missing."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Internal Server Error", "An unexpected error occurred."));
    }
}
