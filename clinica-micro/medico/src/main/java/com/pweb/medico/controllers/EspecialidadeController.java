package com.pweb.medico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.medico.exceptions.EntityNotFoundException;
import com.pweb.medico.services.EspecialidadeService;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {
	
	@Autowired
	private EspecialidadeService especialidadeService;
	
	public ResponseEntity<?> buscarEspecialidade(@RequestParam(required=true) Long id) {
		try {
			especialidadeService.buscarPorId(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
