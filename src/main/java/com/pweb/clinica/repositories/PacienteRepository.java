package com.pweb.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pweb.clinica.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{}
