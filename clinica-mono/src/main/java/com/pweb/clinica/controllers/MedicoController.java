package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.dtos.MedicoGetDTO;
import com.pweb.clinica.dtos.MedicoPostDTO;
import com.pweb.clinica.dtos.MedicoPutDTO;
import com.pweb.clinica.exceptions.DuplicateMedicoException;
import com.pweb.clinica.exceptions.EntityNotFoundException;
import com.pweb.clinica.services.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController implements PessoaController<MedicoPostDTO, MedicoGetDTO, MedicoPutDTO, MedicoDTO> {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping("/todos")
	@Override
	public ResponseEntity<Page<MedicoGetDTO>> listarTodos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<MedicoGetDTO>>(medicoService.getPagina(pageable, "all"), HttpStatus.OK);
	}
	
	@GetMapping("/ativos")
	public ResponseEntity<Page<MedicoGetDTO>> listarAtivos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<MedicoGetDTO>>(medicoService.getPagina(pageable, "active"), HttpStatus.OK);
	}
	
	@GetMapping("/especialidade")
	public ResponseEntity<?> listarPorEspecialidade(@RequestParam("id") Long id) {
		try {
			return new ResponseEntity<List<MedicoGetDTO>>(medicoService.buscarMedicosPorEspecialidade(id), HttpStatus.OK);			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody MedicoPostDTO medicoForm) {
		MedicoDTO medico;
		try {
			medico = medicoService.cadastrar(medicoForm);
			return new ResponseEntity<MedicoDTO>(medico, HttpStatus.CREATED);
		} catch (DuplicateMedicoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getResponse());
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping("/")
	@Override
	public ResponseEntity<?> atualizar(@RequestParam(required=true) Long id, @Valid @RequestBody MedicoPutDTO medicoForm) {
		MedicoDTO medico;
		try {
			medico = medicoService.atualizar(id, medicoForm);
			return new ResponseEntity<MedicoDTO>(medico, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam(required=true) Long id) {
		try {
			medicoService.tornarInativo(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
