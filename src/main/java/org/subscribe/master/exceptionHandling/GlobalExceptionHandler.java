package org.subscribe.master.exceptionHandling;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.subscribe.master.exceptionHandling.customExceptions.BadCredentialsException;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        ErrorResponseDTO responseDTO = generateErrorResponse(exception, request, HttpStatus.NOT_FOUND, "Resource not found");
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> badCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        ErrorResponseDTO responseDTO = generateErrorResponse(exception, request, HttpStatus.FORBIDDEN, "Bad credentials");
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> conflictException(ConflictException exception, HttpServletRequest request) {
        ErrorResponseDTO responseDTO = generateErrorResponse(exception, request, HttpStatus.CONFLICT, "Conflict error");
        return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
    }



    private ErrorResponseDTO generateErrorResponse(Exception e, HttpServletRequest request, HttpStatus status, String title) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setTitle(title);
        responseDTO.setStatus(status.value());
        responseDTO.setDetail(e.getMessage());
        responseDTO.setPath(request.getRequestURI());
        responseDTO.setTimestamp(Instant.now());

        return responseDTO;
    }
}
