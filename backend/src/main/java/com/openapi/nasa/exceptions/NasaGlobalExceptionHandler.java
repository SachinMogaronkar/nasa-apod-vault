package com.openapi.nasa.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NasaGlobalExceptionHandler {
    @ExceptionHandler(NasaNotFoundException.class)
    public ResponseEntity<NasaErrorResponse> handleNasaNotFoundException(
            NasaNotFoundException ex,
            HttpServletRequest request) {

        NasaErrorResponse response = new NasaErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<NasaErrorResponse> handleExceptions(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();

        NasaErrorResponse response = new NasaErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(), // temporarily show real error
                request.getRequestURI(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
