package com.spring.boot.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFoundException(Exception exception, WebRequest request){
        return new ResponseEntity<Object>("Error Occurred while fetching the Resource:"+exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleNumberFormatException(Exception exception, WebRequest request){
        return new ResponseEntity<Object>("Error Occurred while processing the Input Parameters:"+exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({ProductValidationException.class})
    public ResponseEntity<Object> handleProductValidationException(Exception exception, WebRequest request){
        return new ResponseEntity<Object>("Error Occurred while Creating/Updating the Product:"+exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
}
