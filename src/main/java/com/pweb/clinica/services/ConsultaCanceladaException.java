package com.pweb.clinica.services;

import com.pweb.clinica.enums.MotivoCancelamento;

public class ConsultaCanceladaException extends Exception {
	public ConsultaCanceladaException(MotivoCancelamento motivo) {
		super("A consulta já se encontra cancelada pelo motivo: " + motivo.toString());
	}
}
