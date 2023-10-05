package com.pweb.clinica.controllers;

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
import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.dtos.PacienteGetDTO;
import com.pweb.clinica.dtos.PacientePostDTO;
import com.pweb.clinica.dtos.PacientePutDTO;
import com.pweb.clinica.exceptions.DuplicateMedicoException;
import com.pweb.clinica.exceptions.DuplicatePacienteException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.services.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements PessoaController<PacientePostDTO, PacienteGetDTO, PacientePutDTO, PacienteDTO> {

	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	@Override
	public ResponseEntity<Page<PacienteGetDTO>> listar(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<PacienteGetDTO>>(pacienteService.getPagina(pageable), HttpStatus.OK);
	}

	@PostMapping
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody PacientePostDTO pacienteForm) {
		PacienteDTO paciente;
		try {
			paciente = pacienteService.cadastrar(pacienteForm);
			return new ResponseEntity<PacienteDTO>(paciente, HttpStatus.CREATED);
		} catch(DuplicatePacienteException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getResponse());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/")
	@Override
	public ResponseEntity<?> atualizar(@RequestParam("id") Long id,
			@Valid @RequestBody PacientePutDTO pacienteForm) {
		PacienteDTO paciente;
		try {
			paciente = pacienteService.atualizar(id, pacienteForm);
			return new ResponseEntity<PacienteDTO>(paciente, HttpStatus.OK);
		} catch (PacienteNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam("id") Long id) {
		if (this.pacienteService.tornarInativo(id) == null) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
