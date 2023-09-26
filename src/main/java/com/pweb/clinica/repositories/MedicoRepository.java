package com.pweb.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {}
