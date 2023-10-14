package com.pweb.endereco.dtos;

import jakarta.validation.constraints.NotNull;

public record EnderecoPutDTO(
		@NotNull Long enderecoAtual,
		@NotNull EnderecoPostDTO enderecoNovo) {}
