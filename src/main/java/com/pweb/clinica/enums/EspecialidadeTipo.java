package com.pweb.clinica.enums;

import java.util.Arrays;
import java.util.List;

public enum EspecialidadeTipo {
	ORTOPEDIA,
	CARDIOLOGIA,
	GINECOLOGIA,
	DERMATOLOGIA;
	
	public static List<EspecialidadeTipo> obterTipos() {
        return Arrays.asList(EspecialidadeTipo.values());
    }
}
