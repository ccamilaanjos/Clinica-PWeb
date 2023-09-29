package com.pweb.clinica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.pweb.clinica.dtos.MedicoPostDTO;
import com.pweb.clinica.dtos.MedicoPutDTO;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.services.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController implements PessoaController<MedicoPostDTO, MedicoPutDTO, MedicoDTO> {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@Override
	public ResponseEntity<Page<MedicoDTO>> listar(@PageableDefault(size = 10, direction = Direction.ASC, sort = "nome") Pageable pageable) {
		return new ResponseEntity<Page<MedicoDTO>>(medicoService.getPagina(pageable), HttpStatus.OK);
	}
	
	@PostMapping
	@Override
	public ResponseEntity<MedicoDTO> cadastrar(@Valid @RequestBody MedicoPostDTO medicoForm) {
		Medico medico = medicoService.cadastrar(medicoForm);
		return new ResponseEntity<MedicoDTO>(new MedicoDTO(medico), HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	@Override
	public ResponseEntity<?> atualizar(@RequestParam(required=true) Long id, @Valid @RequestBody MedicoPutDTO medicoForm) {
		try {
			Medico medico = medicoService.atualizar(id, medicoForm);
			return new ResponseEntity<MedicoDTO>(new MedicoDTO(medico), HttpStatus.OK);
		} catch (MedicoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam(required=true) Long id) {
		if(medicoService.tornarInativo(id) == null) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
