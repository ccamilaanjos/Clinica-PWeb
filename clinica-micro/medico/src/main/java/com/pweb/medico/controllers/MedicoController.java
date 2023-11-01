package com.pweb.medico.controllers;

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

import com.pweb.medico.dtos.MedicoDTO;
import com.pweb.medico.dtos.MedicoGetDTO;
import com.pweb.medico.dtos.MedicoPostDTO;
import com.pweb.medico.dtos.MedicoPutDTO;
import com.pweb.medico.services.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoService medicoService;

	@GetMapping("/todos")
	public ResponseEntity<Page<MedicoGetDTO>> listarTodos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<MedicoGetDTO>>(medicoService.getPagina(pageable, "all"), HttpStatus.OK);
	}

	@GetMapping("/ativos")
	public ResponseEntity<Page<MedicoGetDTO>> listarAtivos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
		return new ResponseEntity<Page<MedicoGetDTO>>(medicoService.getPagina(pageable, "active"), HttpStatus.OK);
	}

	@GetMapping("/especialidade/")
	public ResponseEntity<?> listarAtivosPorEspecialidade(@RequestParam(required = true) Long id) {
		List<Long> medicos = medicoService.buscarMedicosAtivosPorEspecialidade(id);
		return new ResponseEntity<List<Long>>(medicos, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<MedicoGetDTO> buscarAtivoPorId(@RequestParam(required = true) Long id) {
		MedicoGetDTO medico = new MedicoGetDTO(medicoService.buscarMedicoAtivo(id));
		return new ResponseEntity<MedicoGetDTO>(medico, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@Valid @RequestBody MedicoPostDTO medicoForm) {
		MedicoDTO medico = medicoService.cadastrar(medicoForm);
		return new ResponseEntity<MedicoDTO>(medico, HttpStatus.CREATED);
	}

	@PutMapping("/")
	public ResponseEntity<?> atualizar(@RequestParam(required = true) Long id,
			@Valid @RequestBody MedicoPutDTO medicoForm) {
		MedicoDTO medico = medicoService.atualizar(id, medicoForm);
		return new ResponseEntity<MedicoDTO>(medico, HttpStatus.OK);
	}

	@DeleteMapping("/")
	public ResponseEntity<?> remover(@RequestParam(required = true) Long id) {
		medicoService.tornarInativo(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
