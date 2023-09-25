package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.models.Pessoa;
import com.pweb.clinica.repositories.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements PessoaController {
	
	@Autowired
	private PacienteRepository repository;

	@GetMapping
	@Override
	public List<Paciente> listar() {
		return this.repository.findAll();
	}
	
	@PostMapping
	@Override
	public ResponseEntity<Pessoa> cadastrar() {
		return null;
	}
	
	@PutMapping
	@Override
	public ResponseEntity<Pessoa> atualizar() {
		return null;
	}

	// TODO
	@Override
	public ResponseEntity<Pessoa> remover() {
		return null;
	}

}
