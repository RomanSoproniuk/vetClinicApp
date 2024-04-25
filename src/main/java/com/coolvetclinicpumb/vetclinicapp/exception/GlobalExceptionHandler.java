package com.coolvetclinicpumb.vetclinicapp.exception;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class,
            FileFormatNotSupported.class,
            IOException.class,
            JAXBException.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus notFound = HttpStatus.BAD_REQUEST;
        body.put("timestamp", LocalDateTime.now());
        body.put("status", notFound);
        body.put("error", ex.getClass());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, notFound);
    }
}
