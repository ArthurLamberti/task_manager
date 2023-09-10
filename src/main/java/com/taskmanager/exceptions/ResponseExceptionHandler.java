package com.taskmanager.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            AccountStatusException.class,
            AccessDeniedException.class,
            MissingCsrfTokenException.class
    })
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ae) {
        log.info("Auth error", ae);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .message(ae.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ErrorResponse> handleAccessDeniedException(NoHandlerFoundException ex) {
        log.info("This API endpoint is not found.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        ErrorResponse er = new ErrorResponse();
        List<String> validationErrorsDTO = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            validationErrorsDTO.add(e.getField() + ": " + message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .message(exception.getMessage())
                                .reasons(validationErrorsDTO)
                                .build()
                );
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(ValidationException exception) {
        ErrorResponse er = new ErrorResponse();
        List<String> validationErrorsDTO = new ArrayList<>();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .message(exception.getMessage())
                                .reasons(validationErrorsDTO)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleDefault(Exception ex) {
        log.info("Generic error", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .build()
                );
    }

}
