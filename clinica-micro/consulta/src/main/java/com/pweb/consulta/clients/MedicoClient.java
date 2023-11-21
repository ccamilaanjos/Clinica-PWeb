package com.pweb.consulta.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("medico-ms")
public interface MedicoClient {
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/")
	public ResponseEntity<MedicoGetDTO> buscarAtivoPorId(@RequestParam(required=true) Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/{crm}")
	public ResponseEntity<MedicoGetDTO> buscarAtivoPorCrm(@PathVariable(required=true) String crm);
	
	@RequestMapping(method = RequestMethod.GET, value = "/especialidades/{nome}")
	public ResponseEntity<Long> buscarEspecialidadePorNome(@PathVariable(required=true) String nome);
	
	@RequestMapping(method = RequestMethod.GET, value = "/medicos/especialidade/")
	public ResponseEntity<List<Long>> listarAtivosPorEspecialidade(@RequestParam(required=true) Long id);
}
