package com.pweb.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pweb.clinica.models.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {}
