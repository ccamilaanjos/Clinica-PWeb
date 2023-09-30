package com.pweb.clinica.converters;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.models.Paciente;

public class PacienteConverter {
	public static PacienteDTO converterParaDTO(Paciente paciente) {
		return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				paciente.getEndereco(), paciente.getAtivo());
	}
}
