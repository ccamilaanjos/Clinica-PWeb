package com.pweb.consulta.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pweb.consulta.models.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    List<Consulta> findByDataAndHorarioAndMedicoId(LocalDate data, LocalTime horario, Long medicoId);
    List<Consulta> findByDataAndPacienteIdAndMotivoCancelamentoNull(LocalDate data, Long pacienteId);
    
    @Query("SELECT c FROM consultas c "
    		+ "WHERE c.medicoId =:medicoId "
    		+ "AND (c.data = :data AND (c.horario > :min AND c.horario < :max) "
    		+ "AND c.motivoCancelamento IS NULL)")
    public List<Consulta> verificarConsultasNesteHorario (
    		@Param("medicoId") Long medicoId,
    		@Param("data") LocalDate data,
    		@Param("min") LocalTime min,
    		@Param("max") LocalTime max);
    
    @Query("SELECT c.medicoId FROM consultas c "
    		+ "WHERE (c.data = :data AND (c.horario > :min AND c.horario < :max) "
    		+ "AND c.motivoCancelamento IS NULL)")
    public List<Long> verificarMedicosOcupados (
    		@Param("data") LocalDate data,
    		@Param("min") LocalTime min,
    		@Param("max") LocalTime max);
}
