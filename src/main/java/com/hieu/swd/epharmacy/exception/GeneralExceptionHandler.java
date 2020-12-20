package com.hieu.swd.epharmacy.exception;

import com.hieu.swd.epharmacy.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.TimeZone;

@ControllerAdvice
public class GeneralExceptionHandler {

    private static String timeStamp = Calendar.getInstance(TimeZone.getTimeZone("Asia/Saigon")).getTime().toString();

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<GeneralResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setDescription(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectExistedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<GeneralResponse> handleObjectExistedException(ObjectExistedException ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setDescription(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<GeneralResponse> handleNoResultException(NoResultException ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setDescription(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setMessage("No object returned");
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GeneralResponse> handleBadCredentialsException(BadCredentialsException ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setDescription(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GeneralResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setDescription(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<GeneralResponse> handleNumberFormatException(NumberFormatException ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDescription(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage("Enter an integer - not string");
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GeneralResponse> handleConstraintViolationException(ConstraintViolationException e) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDescription(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage("Validation violated - re-check validation");
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<GeneralResponse> handleBadRequest(Exception ex) {
        GeneralResponse error = new GeneralResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDescription(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(timeStamp);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
