package com.pweb.clinica.validators;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaValidator {
	final static int HORARIO_ABERTURA = 7;
	final static int HORARIO_FECHAMENTO = 19;
	final static DayOfWeek DIA_INICIAL = DayOfWeek.MONDAY;
	final static DayOfWeek DIA_FINAL = DayOfWeek.SATURDAY;	
	
	public static Boolean emHorarioDeFuncionamento() {
		LocalDate data = LocalDate.now();
		LocalTime horario = LocalTime.now();
		DayOfWeek dia = data.getDayOfWeek();
		
//		System.out.println("\n\n\n");
//		System.out.println(data);
//		System.out.println(horario.getHour());
//		System.out.println(dia);
//		System.out.println("\n\n\n");
		
		if(dia.compareTo(DIA_INICIAL) < 0 || dia.compareTo(DIA_FINAL) > 0) {
			return false;
		}
		
		if(horario.getHour() < HORARIO_ABERTURA || horario.getHour() > HORARIO_FECHAMENTO) {
			return false;
		}
		return true;
	}
	
	
}
