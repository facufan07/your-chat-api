package com.facundo.your_chat_api.exception;

import com.facundo.your_chat_api.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleObjectNotFoundException(ObjectNotFoundException e,
                                                           HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Objeto no encontrado");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException e,
                                                           HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Error con el token");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

    }

    @ExceptionHandler(PasswordsDoesNotMatchException.class)
    public ResponseEntity<?> handlePasswordsDoesNotMatchException(PasswordsDoesNotMatchException e,
                                                         HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Las contraseñas no coinciden");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(PasswordsDoesNotHaveTextException.class)
    public ResponseEntity<?> handlePasswordsDoesNotHaveTextException(PasswordsDoesNotHaveTextException e,
                                                                  HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("La/Las contraseña/as no tiene/en texto");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e,
                                                    HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("No autorizado para realizar esta acción");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e,
                                                    HttpServletRequest request) {

        ApiError apiError = new ApiError();

        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Error interno del servidor, intentelo mas tarde");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);

    }
}
