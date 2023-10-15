package com.pweb.consulta.exceptions;

import com.pweb.consulta.enums.MotivoCancelamento;

public class ConsultaCanceladaException extends Exception {
	public ConsultaCanceladaException(MotivoCancelamento motivo) {
		super("A consulta já se encontra cancelada pelo motivo: " + motivo.toString());
	}
}
