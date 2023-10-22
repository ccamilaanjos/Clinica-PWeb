package com.pweb.consulta.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> handlePacienteNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		
		setFields(e, error, "Resource Not Found", HttpStatus.NOT_FOUND, request);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(
			{ClinicaUnavailableException.class,
				ConflictingScheduleException.class,
				EmptyListException.class,
				ConsultaNotFoundException.class,
				ConsultaCanceladaException.class})
	public ResponseEntity<StandardError> handleDuplicatePacienteException(RuntimeException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		
		setFields(e, error, "Unavailable Resource", HttpStatus.BAD_REQUEST, request);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	private void setFields(RuntimeException e,
			StandardError error,
			String message,
			HttpStatus status,
			HttpServletRequest request) {
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(message);
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	}
}
