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
import com.pweb.clinica.dtos.MedicoFormDTO;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.services.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController implements PessoaController<MedicoFormDTO, MedicoDTO> {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@Override
	public ResponseEntity<Page<MedicoDTO>> listar(@PageableDefault(size = 10, direction = Direction.ASC, sort = "nome") Pageable pageable) {
		return new ResponseEntity<Page<MedicoDTO>>(medicoService.getPagina(pageable), HttpStatus.OK);
	}
	
	@PostMapping
	@Override
	public ResponseEntity<MedicoDTO> cadastrar(@RequestBody MedicoFormDTO medicoForm) {
		Medico medico = medicoService.cadastrar(medicoForm);
		return new ResponseEntity<MedicoDTO>(new MedicoDTO(medico), HttpStatus.CREATED);
	}
	
	@PutMapping
	@Override
	public ResponseEntity<MedicoDTO> atualizar(@RequestParam("id") Long id, @RequestBody MedicoFormDTO medicoForm) {
		Medico medico = medicoService.atualizar(id, medicoForm);
		return new ResponseEntity<MedicoDTO>(new MedicoDTO(medico), HttpStatus.OK);
	}

	@DeleteMapping("/")
	@Override
	public ResponseEntity<?> remover(@RequestParam("id") Long id) {
		if(medicoService.tornarInativo(id) == null) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
