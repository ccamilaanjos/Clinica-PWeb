package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.models.Pessoa;
import com.pweb.clinica.services.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements PessoaController {
	
	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	@Override
	public List<PacienteDTO> listar() {
		return this.pacienteService.getListaOrdenadaPorNome();
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

	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<?> remover(@PathVariable Long id) {
		if(this.pacienteService.tornarInativo(id) == null) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
