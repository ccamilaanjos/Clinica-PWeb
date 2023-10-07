package com.pweb.clinica.validators;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.pweb.clinica.exceptions.ClinicaUnavailableException;

public class ConsultaValidator {
	final static int HORARIO_ABERTURA = 7;
	final static int HORARIO_FECHAMENTO = 19;
	final static DayOfWeek DIA_INICIAL = DayOfWeek.MONDAY;
	final static DayOfWeek DIA_FINAL = DayOfWeek.SATURDAY;
	final static Long MIN_ANTECEDENCIA_MARCACAO = (long) 30;
	
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
	
	public static Boolean emTempoDeMarcacao(LocalDate data, LocalTime horario) {
		LocalDate hoje = LocalDate.now();
		LocalTime agora = LocalTime.now();

		if(!hoje.equals(data)) {
			return true;
		}
		
		if(agora.plusMinutes(MIN_ANTECEDENCIA_MARCACAO).isAfter(horario)) {
			return false;
		}
		
		return true;
	}

	public static Boolean validarRestricoesDeTempo(LocalDate data, LocalTime horario) throws ClinicaUnavailableException {
		if(!emHorarioDeFuncionamento(data, horario)) {
			throw new ClinicaUnavailableException("A clínica não está disponível para marcações no momento informado");
		}
		
		if(!emTempoDeMarcacao(data, horario)) {
			throw new ClinicaUnavailableException("O agendamento precisa ocorrer com pelo menos 30 minutos de antecedência");
		}
		
		return true;
	}
}
