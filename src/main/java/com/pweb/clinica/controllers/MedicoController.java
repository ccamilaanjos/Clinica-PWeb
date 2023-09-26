package com.pweb.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.clinica.dtos.MedicoDTO;
import com.pweb.clinica.dtos.MedicoFormDTO;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Pessoa;
import com.pweb.clinica.services.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController implements PessoaController<MedicoFormDTO> {
	
	@Autowired
	private MedicoService medicoService;

	@GetMapping
	@Override
	public List<MedicoDTO> listar() {
		return this.medicoService.getListaOrdenadaPorNome();
	}
	
	@PostMapping
	@Override
	public ResponseEntity<MedicoDTO> cadastrar(@RequestBody MedicoFormDTO medicoForm) {
		Medico medico = medicoService.cadastrar(medicoForm);
		return new ResponseEntity<MedicoDTO>(new MedicoDTO(medico), HttpStatus.CREATED);
	}
	@Override
	public ResponseEntity<Pessoa> atualizar() {
		return null;
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam("id") Long id) {
		if(this.medicoService.tornarInativo(id) == null) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
