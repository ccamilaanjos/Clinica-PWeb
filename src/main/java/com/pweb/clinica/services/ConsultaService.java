package com.pweb.clinica.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.ConsultaCancelDTO;
import com.pweb.clinica.dtos.ConsultaCreateDTO;
import com.pweb.clinica.enums.MotivoCancelamento;
import com.pweb.clinica.exceptions.ClinicaUnavailableException;
import com.pweb.clinica.exceptions.ConflictingScheduleException;
import com.pweb.clinica.exceptions.ConsultaNotFoundException;
import com.pweb.clinica.exceptions.EmptyListException;
import com.pweb.clinica.exceptions.EntityNotFoundException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.ConsultaRepository;
import com.pweb.clinica.validators.ConsultaValidator;

@Service
public class ConsultaService {
	@Autowired
	private ConsultaValidator consultaValidator;
	@Autowired
	private ConsultaRepository consultaRepository;

	public Consulta marcarConsulta(ConsultaCreateDTO consultaForm)
			throws ClinicaUnavailableException, ConflictingScheduleException,
			EntityNotFoundException, EmptyListException {
		
		Long idMedico = consultaForm.idMedico();
		Long idPaciente = consultaForm.idPaciente();
		Long idEspecialidade = consultaForm.idEspecialidade();
		LocalDate data = consultaForm.data();
		LocalTime horario = consultaForm.horario().withNano(0);
		
		ConsultaValidator.validarRestricoesDeTempoMarcacao(data, horario);
		
		Paciente paciente = consultaValidator.validarPaciente(idPaciente, data);
		Medico medico = consultaValidator.validarMedico(idMedico, data, horario, idEspecialidade);
		
		// TODO: Retornar DTO
		Consulta consulta = new Consulta(paciente, medico, data, horario);
		consultaRepository.save(consulta);
		return consulta;
	}
		
	public Consulta cancelarConsulta(ConsultaCancelDTO consultaForm, Long idConsulta)
			throws ConsultaNotFoundException, ConsultaCanceladaException, ConflictingScheduleException {
		
		Consulta consulta = consultaValidator.validarConsulta(idConsulta);
		ConsultaValidator.validarRestricoesDeTempoCancelamento(consulta.getData(), consulta.getHorario());
		MotivoCancelamento motivo = ConsultaValidator.validarMotivoCancelamento(consultaForm.motivo());
		
		consulta.setMotivoCancelamento(motivo);
		consultaRepository.save(consulta);
		
		// TODO: Retornar DTO
		return consulta;
	}
}
