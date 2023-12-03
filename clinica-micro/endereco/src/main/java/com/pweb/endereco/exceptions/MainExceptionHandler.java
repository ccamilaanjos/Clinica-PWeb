package com.pweb.endereco.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EnderecoNotFoundException.class)
	public ResponseEntity<StandardError> handleEntityNotFoundException(EnderecoNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		setFields(e, error, status, request);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	private void setFields(RuntimeException e, StandardError error, HttpStatus status, HttpServletRequest request) {
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(status.name());
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	}
}
