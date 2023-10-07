package com.pweb.clinica.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	public Optional<Medico> findByCrm(String crm);
	public Optional<List<Medico>> findByEspecialidade_idOrderByNomeAsc(Long id);
	public Optional<List<Medico>> findByEspecialidade_idAndAtivoTrueOrderByNomeAsc(Long id);
    @Query("SELECT m FROM medicos m LEFT JOIN consultas c ON c.medico.id = m.id WHERE m.especialidade.id =:especialidade AND c.data <>:data AND c.horario <>:horario")
    public List<Medico> findAllByEspecialidade_idAndDataAndHorario(
    		@Param("especialidade") Long especialidade, @Param("data") LocalDate data, @Param("horario") LocalTime horario);
}
