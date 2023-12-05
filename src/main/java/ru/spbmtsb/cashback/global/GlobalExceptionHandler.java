package ru.spbmtsb.cashback.global;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.spbmtsb.cashback.error.InsufficientFundsException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = InsufficientFundsException.class)
    protected ResponseEntity<Object> createResponseEntity(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Недостаточно средств на счёте", new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
}
