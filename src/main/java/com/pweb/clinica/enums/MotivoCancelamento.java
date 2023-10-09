package com.pweb.clinica.enums;

import java.util.Arrays;
import java.util.List;

public enum MotivoCancelamento {
	PACIENTE_DESISTIU,
	MEDICO_CANCELOU,
	OUTROS;
	
	public static List<MotivoCancelamento> obterMotivos() {
        return Arrays.asList(MotivoCancelamento.values());
    }
}
