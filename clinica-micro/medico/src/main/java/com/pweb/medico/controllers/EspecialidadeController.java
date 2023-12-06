package com.pweb.medico.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.medico.dtos.EspecialidadeDTO;
import com.pweb.medico.services.EspecialidadeService;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

	@Autowired
	private EspecialidadeService especialidadeService;

	@GetMapping
	public ResponseEntity<EspecialidadeDTO> buscarEspecialidade(@RequestParam(required = true) Long id) {
		EspecialidadeDTO especialidade = new EspecialidadeDTO(especialidadeService.buscarPorId(id));
		return new ResponseEntity<EspecialidadeDTO>(especialidade, HttpStatus.OK);
	}
	
	@GetMapping("/todas")
	public ResponseEntity<List<EspecialidadeDTO>> listarTodas() {
		List<EspecialidadeDTO> especialidades = especialidadeService.buscarTodas();
		return new ResponseEntity<List<EspecialidadeDTO>>(especialidades, HttpStatus.OK);
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity<EspecialidadeDTO> buscarEspecialidadePorNome(@PathVariable(required = true) String nome) {
		EspecialidadeDTO especialidade = new EspecialidadeDTO(especialidadeService.buscarPorTitulo(nome));
		return new ResponseEntity<EspecialidadeDTO>(especialidade, HttpStatus.OK);
	}
}
