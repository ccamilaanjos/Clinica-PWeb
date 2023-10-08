package com.pweb.clinica.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pweb.clinica.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	public Optional<Medico> findByCrm(String crm);
	public Optional<Medico> findByIdAndAtivoTrue(Long id);
	public Page<Medico> findAllByAtivoTrue(Pageable pageable);
	public Optional<List<Medico>> findByEspecialidade_idOrderByNomeAsc(Long id);
	public Optional<List<Medico>> findByEspecialidade_idAndAtivoTrueOrderByNomeAsc(Long id);
	
     @Query("SELECT m FROM medicos m LEFT JOIN consultas c "
    		+ "ON c.medico.id = m.id "
    		+ "WHERE m.especialidade.id =:especialidade "
    		+ "AND m.ativo = TRUE "
    		+ "AND ((c.data = :data AND (c.horario <= :min OR c.horario >= :max)) "
    		+ "OR (c.data IS NULL AND c.horario IS NULL))")
    public List<Medico> findMedicosDisponiveis(
    		@Param("especialidade") Long especialidade,
    		@Param("data") LocalDate data,
    		@Param("min") LocalTime min,
    		@Param("max") LocalTime max);
}

//SELECT * FROM MEDICOS M LEFT JOIN CONSULTAS C
//ON (C.MEDICO_ID = M.ID)
//WHERE M.ESPECIALIDADE_ID = 2 AND
//(C.DATA != '2023-10-07' AND C.HORARIO != '08:00:00')
//OR (C.DATA IS NULL AND C.HORARIO IS NULL);
//
//SELECT * FROM MEDICOS M LEFT JOIN CONSULTAS C
//ON (C.MEDICO_ID = M.ID);
