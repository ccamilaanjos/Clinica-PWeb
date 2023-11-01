package com.pweb.consulta.dtos;

import com.pweb.consulta.clients.PacienteGetDTO;

public record PacienteDTO(Long id, PacienteGetDTO paciente) {}
