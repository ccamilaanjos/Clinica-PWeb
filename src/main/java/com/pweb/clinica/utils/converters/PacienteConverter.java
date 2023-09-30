package com.pweb.clinica.utils.converters;

import com.pweb.clinica.dtos.PacienteDTO;
import com.pweb.clinica.models.Paciente;

public class PacienteConverter {
	public static PacienteDTO converterModelParaDTO(Paciente paciente) {
		return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(),
				paciente.getTelefone(), EnderecoConverter.converterModelParaDTO(paciente.getEndereco()),
				paciente.getAtivo());
	}
}
