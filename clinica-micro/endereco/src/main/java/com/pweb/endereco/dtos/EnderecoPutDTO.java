package com.pweb.endereco.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record EnderecoPutDTO(
		@NotNull Long enderecoAtual,
		@Valid EnderecoPostDTO enderecoNovo) {}
