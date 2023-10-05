package com.pweb.clinica.controllers;

import jakarta.validation.constraints.NotBlank;

public record ConsultaPostDTO(
		@NotBlank(message = "O id do paciente não pode ser nulo ou vazio") Long idPaciente,
		Long idMedico,
		@NotBlank(message = "O id da especialidade não pode ser nulo ou vazio") Long idEspecialidade) {
}
