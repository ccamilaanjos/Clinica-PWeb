package com.pweb.clinica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	public Optional<Paciente> findByCpf(String cpf);
}
