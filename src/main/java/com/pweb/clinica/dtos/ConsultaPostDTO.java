package com.pweb.clinica.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaPostDTO(
		@NotBlank(message = "O id do paciente não pode ser nulo ou vazio") Long idPaciente,
		Long idMedico,
		@NotBlank(message = "O id da especialidade não pode ser nulo ou vazio") Long idEspecialidade,
		@Future @NotNull LocalDate data,
		@Future @NotNull LocalTime horario) {
}
