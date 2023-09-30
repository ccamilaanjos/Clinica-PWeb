package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.utils.converters.EnderecoConverter;

public record PacienteDTO(Long id, String nome, String cpf, String email, String telefone, EnderecoDTO endereco,
		Boolean ativo) {

	public PacienteDTO(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				EnderecoConverter.converterModelParaDTO(paciente.getEndereco()), paciente.getAtivo());
	}
}
