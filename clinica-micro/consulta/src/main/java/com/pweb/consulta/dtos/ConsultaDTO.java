package com.pweb.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pweb.consulta.enums.MotivoCancelamento;
import com.pweb.consulta.models.Consulta;

public record ConsultaDTO(
		Long id,
		Long paciente,
		Long medico,
		LocalDate data,
		LocalTime horario,
		MotivoCancelamento motivo_cancelamento) {
	
	public ConsultaDTO(Consulta consulta, Long paciente, Long medico) {
		this(
				consulta.getId(),
				paciente,
				medico,
				consulta.getData(),
				consulta.getHorario(),
				consulta.getMotivoCancelamento());
	}

}
