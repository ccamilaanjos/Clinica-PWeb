package com.pweb.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("medico-ms")
public interface MedicoClient {
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/")
	public ResponseEntity<?> buscarAtivoPorId(@RequestParam(required=true) Long id);
	@RequestMapping(method = RequestMethod.GET, value = "/especialidades")
	public ResponseEntity<?> buscarEspecialidade(@RequestParam(required=true) Long id);
}
