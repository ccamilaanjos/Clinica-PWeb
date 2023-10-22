package com.pweb.paciente.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PacienteNotFoundException.class)
	public ResponseEntity<StandardError> handlePacienteNotFoundException(PacienteNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		
		setFields(e, error, "Resource Not Found", request);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DuplicatePacienteException.class)
	public ResponseEntity<StandardError> handleDuplicatePacienteException(DuplicatePacienteException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		
		setFields(e, error, "Duplicate", request);

		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	private void setFields(RuntimeException e, StandardError error, String message, HttpServletRequest request) {
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setError(message);
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	}
}
