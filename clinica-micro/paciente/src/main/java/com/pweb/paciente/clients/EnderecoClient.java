package com.pweb.paciente.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@FeignClient("endereco-ms")
public interface EnderecoClient {
	@RequestMapping(method = RequestMethod.GET, value = "/enderecos/")
	public ResponseEntity<EnderecoGetDTO> buscar(@RequestParam(required = true) Long id);
	@RequestMapping(method = RequestMethod.POST, value = "/enderecos")
	public ResponseEntity<Long> cadastrar(@Valid @RequestBody EnderecoPostDTO enderecoForm);
	@RequestMapping(method = RequestMethod.PUT, value = "/enderecos")
	public ResponseEntity<Long> atualizar(@Valid @RequestBody EnderecoPutDTO enderecoForm);
}
