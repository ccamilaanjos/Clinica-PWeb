package com.pweb.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record ConsultaCreateDTO(
		@NotNull (message = "O id do paciente não pode ser nulo ou vazio") Long idPaciente,
		Long idMedico,
		@NotNull(message = "O id da especialidade não pode ser nulo ou vazio") Long idEspecialidade,
		@FutureOrPresent(message = "Data precisa ser programada para o futuro") @NotNull LocalDate data,
		@NotNull LocalTime horario) {
}
