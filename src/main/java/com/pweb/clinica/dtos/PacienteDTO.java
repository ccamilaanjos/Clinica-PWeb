package com.pweb.clinica.dtos;

import com.pweb.clinica.models.Paciente;

public record PacienteDTO(Long id, String nome, String cpf, String email, String telefone, EnderecoDTO endereco,
		Boolean ativo) {

	public PacienteDTO(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getCPF(), paciente.getEmail(), paciente.getTelefone(),
				new EnderecoDTO(paciente.getEndereco()), paciente.getAtivo());
	}
}
