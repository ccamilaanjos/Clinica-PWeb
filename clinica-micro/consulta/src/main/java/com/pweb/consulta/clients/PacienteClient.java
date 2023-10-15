package com.pweb.consulta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("paciente-ms")
public interface PacienteClient {
	@RequestMapping(method = RequestMethod.GET, value = "/pacientes/")
	public ResponseEntity<?> buscarAtivoPorId(@RequestParam(required=true) Long id);
}
