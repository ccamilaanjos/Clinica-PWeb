package com.pweb.consulta.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaCreateDTO(
		@NotNull(message = "O campo cpf não deve ser nulo ou vazio") @CPF(message = "CPF inválido") String cpf,
		String crm,
		@NotBlank(message = "O campo especialidade não pode ser nulo ou vazio") String especialidade,
		@FutureOrPresent(message = "Data precisa ser programada para o futuro") @NotNull LocalDate data,
		@NotNull LocalTime horario) {
}
