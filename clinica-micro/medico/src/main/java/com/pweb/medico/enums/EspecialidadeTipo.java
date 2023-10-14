package com.pweb.medico.enums;

import java.util.Arrays;
import java.util.List;

public enum EspecialidadeTipo {
	ORTOPEDIA,
	CARDIOLOGIA,
	GINECOLOGIA,
	DERMATOLOGIA,
	PNEUMOLOGIA;
	
	public static List<EspecialidadeTipo> obterTipos() {
        return Arrays.asList(EspecialidadeTipo.values());
    }
}
