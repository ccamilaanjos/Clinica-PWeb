package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Pessoa;
import com.pweb.clinica.services.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController implements PessoaController {
	
	@Autowired
	private MedicoService medicoService;

	@GetMapping
	@Override
	public List<MedicoDTO> listar() {
		return this.medicoService.getListaOrdenadaPorNome();
	}
	
	// TODO:
	@Override
	public ResponseEntity<Medico> cadastrar() {
		return null;
	}

	@Override
	public ResponseEntity<Pessoa> atualizar() {
		return null;
	}

	@Override
	public ResponseEntity<Pessoa> remover() {
		return null;
	}

}
