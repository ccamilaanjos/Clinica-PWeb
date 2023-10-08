package com.pweb.clinica.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    List<Consulta> findByDataAndHorarioAndMedico_id(LocalDate data, LocalTime horario, Long medicoId);
    List<Consulta> findByDataAndPaciente_id(LocalDate data, Long pacienteId);
}
