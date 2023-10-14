package com.pweb.clinica.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pweb.clinica.dtos.PacienteDTO;

public class DuplicatePacienteException extends Exception {
	private final PacienteDTO paciente;
	private final String message = "CPF já cadastrado";

	public DuplicatePacienteException(PacienteDTO paciente) {
		 this.paciente = paciente;
	}
	
	public Map<String, Object> getResponse() {
		
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("message", message);
		response.put("object", paciente);
		
		return response;
	}
}
