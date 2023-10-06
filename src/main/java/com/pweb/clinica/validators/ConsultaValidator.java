package com.pweb.clinica.validators;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaValidator {
	final static int HORARIO_ABERTURA = 7;
	final static int HORARIO_FECHAMENTO = 19;
	final static DayOfWeek DIA_INICIAL = DayOfWeek.MONDAY;
	final static DayOfWeek DIA_FINAL = DayOfWeek.SATURDAY;
	
	public static Boolean emHorarioDeFuncionamento(LocalDate data, LocalTime horario) {
		DayOfWeek dia = data.getDayOfWeek();
		
		if(dia.compareTo(DIA_INICIAL) < 0 || dia.compareTo(DIA_FINAL) > 0) {
			return false;
		}
		
		if(horario.getHour() < HORARIO_ABERTURA || horario.getHour() > (HORARIO_FECHAMENTO - 1)) {
			return false;
		}
		
		return true;
	}
	
	public static LocalTime zerarNanos(LocalTime horario) {
		return horario.withNano(0);
	}
}
