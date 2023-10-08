package com.pweb.clinica.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Medico;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    List<Consulta> findByDataAndHorarioAndMedico_id(LocalDate data, LocalTime horario, Long medicoId);
    List<Consulta> findByDataAndPaciente_id(LocalDate data, Long pacienteId);
    
    @Query("SELECT c FROM consultas c "
    		+ "WHERE c.medico.id =:medicoId "
    		+ "AND (c.data = :data AND (c.horario > :min AND c.horario < :max))")
    public List<Consulta> verificarConsultasNesteHorario (
    		@Param("medicoId") Long especialidade,
    		@Param("data") LocalDate data,
    		@Param("min") LocalTime min,
    		@Param("max") LocalTime max);
}
