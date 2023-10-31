package com.pweb.paciente.exceptions;

import java.time.Instant;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PacienteNotFoundException.class)
	protected ResponseEntity<StandardError> handlePacienteNotFoundException(PacienteNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		setFields(e, error, status, request);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DuplicatePacienteException.class)
	protected ResponseEntity<StandardError> handleDuplicatePacienteException(DuplicatePacienteException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.CONFLICT;
		
		setFields(e, error, status, request);

		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		StandardError error = new StandardError();
		status = HttpStatus.BAD_REQUEST;
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(ex.getMessage());
		error.setMessage(ex.getAllErrors().get(0).getDefaultMessage());
		
		String path = request.getDescription(false);
		path = path.substring(path.indexOf("=") + 1); 
		error.setPath(path);

		return ResponseEntity.status(status).body(error);
	}
	
	protected void setFields(RuntimeException e, StandardError error, HttpStatus status, HttpServletRequest request) {
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(status.name());
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	}
}
