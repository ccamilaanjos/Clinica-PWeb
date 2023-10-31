package com.pweb.medico.exceptions;

import java.time.Instant;

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
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({MedicoNotFoundException.class, EspecialidadeNotFoundException.class})
	protected ResponseEntity<StandardError> handlePacienteNotFoundException(RuntimeException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		setFields(e, error, status, request);

		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DuplicateMedicoException.class)
	protected ResponseEntity<StandardError> handleDuplicatePacienteException(DuplicateMedicoException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.CONFLICT;
		
		setFields(e, error, status , request);

		return ResponseEntity.status(status).body(error);
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
	
	protected void setFields(RuntimeException e, StandardError error,	HttpStatus status, HttpServletRequest request) {
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(status.name());
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	}
}
