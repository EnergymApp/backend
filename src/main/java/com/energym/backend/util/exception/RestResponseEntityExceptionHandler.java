package com.energym.backend.util.exception;

import org.hibernate.id.IdentifierGenerationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> handlerNoSuchElementFoundException(RuntimeException ex, WebRequest request){
        String body = "Elemento no encontrado";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<Object> handlerNumberFormatException(RuntimeException ex, WebRequest request){
        String body = "El formato del id no es válido, debe ser un número entero";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = EncryptException.class)
    public ResponseEntity<Object> handlerEncryptException(RuntimeException ex, WebRequest request){
        String body = "Error en la encriptación del password";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handlerIllegalArgumentException(RuntimeException ex, WebRequest request){
        String body = "Verifique que todos los datos necesarios del objeto estén completos";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
