package com.pweb.medico.clients;

import jakarta.validation.constraints.NotNull;

public record EnderecoPutDTO(
			@NotNull Long enderecoAtual,
			@NotNull EnderecoPostDTO enderecoNovo) {}