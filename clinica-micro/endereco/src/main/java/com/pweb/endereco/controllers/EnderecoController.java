package com.pweb.endereco.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.endereco.dtos.EnderecoPostDTO;
import com.pweb.endereco.dtos.EnderecoPutDTO;
import com.pweb.endereco.models.Endereco;
import com.pweb.endereco.services.EnderecoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@Valid @RequestBody EnderecoPostDTO enderecoForm) {
		Endereco endereco;
		try {
			endereco = enderecoService.atribuirEndereco(enderecoForm);
			return new ResponseEntity<Long>(endereco.getId(), HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@Valid @RequestBody EnderecoPutDTO enderecos) {
		Endereco endereco;
		try {
			endereco = enderecoService.ajustarCampos(enderecos);
			return new ResponseEntity<Long>(endereco.getId(), HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/porta")
		public String retornaPorta(@Value("${local.server.port}") String porta){
			return String.format("Requisição respondida pela instância executando na porta %s", porta);
	}
}
