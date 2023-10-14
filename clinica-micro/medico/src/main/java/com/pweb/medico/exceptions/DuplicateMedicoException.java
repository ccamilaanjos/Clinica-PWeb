package com.pweb.medico.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pweb.medico.dtos.MedicoDTO;

public class DuplicateMedicoException extends Exception {
	private final MedicoDTO medico;
	private final String message = "CRM já cadastrado";

	public DuplicateMedicoException(MedicoDTO medico) {
		this.medico = medico;
	}
	
	public Map<String, Object> getResponse() {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("message", message);
	    response.put("object", medico);
	    
	    return response;
	}
}
