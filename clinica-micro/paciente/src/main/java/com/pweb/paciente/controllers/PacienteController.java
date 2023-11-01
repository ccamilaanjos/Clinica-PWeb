package com.pweb.paciente.controllers;

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

import com.pweb.paciente.dtos.PacienteDTO;
import com.pweb.paciente.dtos.PacienteGetDTO;
import com.pweb.paciente.dtos.PacientePostDTO;
import com.pweb.paciente.dtos.PacientePutDTO;
import com.pweb.paciente.services.PacienteService;
import com.pweb.pessoa.controllers.PessoaController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements PessoaController<PacientePostDTO, PacienteGetDTO, PacientePutDTO, PacienteDTO> {

	@Autowired
	private PacienteService pacienteService;

	@GetMapping("/todos")
	@Override
	public ResponseEntity<Page<PacienteGetDTO>> listarTodos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<PacienteGetDTO>>(pacienteService.getPagina(pageable, "all"), HttpStatus.OK);
	}
	
	@GetMapping("/ativos")
	@Override
	public ResponseEntity<Page<PacienteGetDTO>> listarAtivos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<PacienteGetDTO>>(pacienteService.getPagina(pageable, "active"), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PacienteGetDTO> buscarAtivoPorId(@RequestParam(required=true) Long id) {
		PacienteGetDTO paciente = new PacienteGetDTO(pacienteService.buscarPacienteAtivo(id));
		return new ResponseEntity<PacienteGetDTO>(paciente, HttpStatus.OK);
	}

	@PostMapping
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody PacientePostDTO pacienteForm) {
		PacienteDTO paciente = pacienteService.cadastrar(pacienteForm);
		return new ResponseEntity<PacienteDTO>(paciente, HttpStatus.CREATED);
	}

	@PutMapping("/")
	@Override
	public ResponseEntity<?> atualizar(@RequestParam(required=true) Long id,
			@Valid @RequestBody PacientePutDTO pacienteForm) {
		PacienteDTO paciente = pacienteService.atualizar(id, pacienteForm);
		return new ResponseEntity<PacienteDTO>(paciente, HttpStatus.OK);
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam(required=true) Long id) {
		pacienteService.tornarInativo(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
