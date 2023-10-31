package com.pweb.consulta.exceptions;

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

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		setFields(e, error, status, request);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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
	
	@ExceptionHandler(FeignException.class)
	protected ResponseEntity<StandardError> handleFeignExceptions(FeignException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String[] mes = e.getMessage().split("\"message\":\"");
        
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Client exception");
		error.setMessage(mes[1].split("\"")[0]);
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);

	}
	
	@ExceptionHandler(
			{ClinicaUnavailableException.class,
				ConflictingScheduleException.class,
				EmptyListException.class,
				ConsultaNotFoundException.class,
				ConsultaCanceladaException.class})
	public ResponseEntity<StandardError> handleBadRequests(RuntimeException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		setFields(e, error, status, request);

		return ResponseEntity.status(status).body(error);
	}
	
	private void setFields(RuntimeException e, StandardError error, HttpStatus status, HttpServletRequest request) {
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(status.name());
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	}
}
