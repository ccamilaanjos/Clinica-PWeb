package com.pweb.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pweb.consulta.enums.MotivoCancelamento;
import com.pweb.consulta.models.Consulta;

public record ConsultaGetDTO(Long id, PacienteGetDTO paciente, MedicoGetDTO medico, LocalDate data, LocalTime horario,
		MotivoCancelamento motivo_cancelamento) {

	public ConsultaGetDTO(Consulta consulta, PacienteGetDTO paciente, MedicoGetDTO medico) {
		this(consulta.getId(), paciente, medico, consulta.getData(), consulta.getHorario(),
				consulta.getMotivoCancelamento());
	}
}
