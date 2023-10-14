package com.pweb.medico.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("endereco-ms")
public interface EnderecoClient {
	@RequestMapping(method = RequestMethod.POST, value = "/enderecos")
	public ResponseEntity<Long> cadastrar(@RequestBody EnderecoPostDTO enderecoForm);
	@RequestMapping(method = RequestMethod.PUT, value = "/enderecos")
	public ResponseEntity<Long> atualizar(@RequestBody EnderecoPutDTO enderecoForm);
}
