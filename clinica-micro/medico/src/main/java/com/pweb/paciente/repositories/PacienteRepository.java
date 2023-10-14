package com.pweb.paciente.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.paciente.models.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	public Optional<Paciente> findByCpf(String cpf);
	public Optional<Paciente> findByIdAndAtivoTrue(Long id);
	public Page<Paciente> findAllByAtivoTrue(Pageable pageable);
}
