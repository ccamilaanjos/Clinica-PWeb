package com.pweb.medico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.medico.models.Especialidade;
import com.pweb.medico.services.EspecialidadeService;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

	@Autowired
	private EspecialidadeService especialidadeService;

	@GetMapping
	public ResponseEntity<?> buscarEspecialidade(@RequestParam(required = true) Long id) {
		especialidadeService.buscarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity<Long> buscarEspecialidadePorNome(@PathVariable(required = true) String nome) {
		Especialidade especialidade = especialidadeService.buscarPorTitulo(nome);
		return new ResponseEntity<Long>(especialidade.getId(), HttpStatus.OK);
	}
	
}
