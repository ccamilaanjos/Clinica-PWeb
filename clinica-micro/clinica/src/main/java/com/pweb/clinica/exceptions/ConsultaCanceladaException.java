package com.pweb.clinica.exceptions;

import com.pweb.clinica.enums.MotivoCancelamento;

public class ConsultaCanceladaException extends Exception {
	public ConsultaCanceladaException(MotivoCancelamento motivo) {
		super("A consulta já se encontra cancelada pelo motivo: " + motivo.toString());
	}
}
