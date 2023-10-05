package com.pweb.clinica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.exceptions.EmptyListException;
import com.pweb.clinica.exceptions.EspecialidadeNotFoundException;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.services.ConsultaService;

@RestController
@RequestMapping
public class ConsultaController {
	@Autowired
	private ConsultaService consultaService;

	@PostMapping("/marcarConsulta")
	public ResponseEntity<?> marcarConsulta(
			@RequestBody ConsultaPostDTO consultaForm) {
		try {
			Consulta consulta = consultaService.marcarConsulta(consultaForm);
			return ResponseEntity.status(HttpStatus.CREATED).body(consulta);	
		} catch (EmptyListException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());		
		} catch (PacienteNotFoundException | MedicoNotFoundException | EspecialidadeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/cancelarConsulta")
	public ResponseEntity<?> cancelarConsulta(@RequestParam("id") Long id) {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
