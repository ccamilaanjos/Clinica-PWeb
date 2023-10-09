package com.pweb.clinica.dtos;

import jakarta.validation.constraints.NotBlank;

public record ConsultaCancelDTO(
		@NotBlank(message = "O motivo do cancelamento não pode ser nulo ou vazio") String motivo) {
}
