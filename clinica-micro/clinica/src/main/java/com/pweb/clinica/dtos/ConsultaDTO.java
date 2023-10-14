package com.pweb.clinica.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pweb.clinica.enums.MotivoCancelamento;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;

public record ConsultaDTO(
		Long id,
		PacienteGetDTO paciente,
		MedicoGetDTO medico,
		LocalDate data,
		LocalTime horario,
		MotivoCancelamento motivo_cancelamento) {
	
	public ConsultaDTO(Consulta consulta, Paciente paciente, Medico medico, Especialidade especialidade) {
		this(
				consulta.getId(),
				new PacienteGetDTO(paciente),
				new MedicoGetDTO(medico, especialidade),
				consulta.getData(),
				consulta.getHorario(),
				consulta.getMotivoCancelamento());
	}

}
