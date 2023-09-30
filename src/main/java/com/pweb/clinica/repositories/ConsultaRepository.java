package com.pweb.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{}
