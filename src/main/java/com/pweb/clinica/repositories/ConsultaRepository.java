package com.pweb.clinica.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
	// @Query("SELECT c FROM Consulta c WHERE c.data = :data AND c.horario = :horario AND c.medico_id = :medico_id")
    List<Consulta> findByDataAndHorarioAndMedico_id(LocalDate data, LocalTime horario, Long medicoId);
}
