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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.dtos.PacienteFormDTO;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.models.Pessoa;
import com.pweb.clinica.services.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements PessoaController<PacienteFormDTO> {
	
	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	@Override
	public List<PacienteDTO> listar() {
		return this.pacienteService.getListaOrdenadaPorNome();
	}
	
	@PostMapping
	@Override
	public ResponseEntity<PacienteDTO> cadastrar(@RequestBody PacienteFormDTO pacienteForm) {
		Paciente paciente = pacienteService.cadastrar(pacienteForm);
		return new ResponseEntity<PacienteDTO>(new PacienteDTO(paciente), HttpStatus.CREATED);
	}
	
	@PutMapping
	@Override
	public ResponseEntity<Pessoa> atualizar() {
		return null;
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam("id") Long id) {
		if(this.pacienteService.tornarInativo(id) == null) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
