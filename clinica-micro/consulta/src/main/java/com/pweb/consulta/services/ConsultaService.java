package com.pweb.consulta.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.consulta.dtos.ConsultaCancelDTO;
import com.pweb.consulta.dtos.ConsultaCreateDTO;
import com.pweb.consulta.dtos.ConsultaDTO;
import com.pweb.consulta.enums.MotivoCancelamento;
import com.pweb.consulta.exceptions.ClinicaUnavailableException;
import com.pweb.consulta.exceptions.ConflictingScheduleException;
import com.pweb.consulta.exceptions.ConsultaCanceladaException;
import com.pweb.consulta.exceptions.ConsultaNotFoundException;
import com.pweb.consulta.exceptions.EmptyListException;
import com.pweb.consulta.exceptions.EntityNotFoundException;
import com.pweb.consulta.models.Consulta;
import com.pweb.consulta.repositories.ConsultaRepository;
import com.pweb.consulta.validators.ConsultaValidator;

@Service
public class ConsultaService {
	@Autowired
	private ConsultaValidator consultaValidator;
	@Autowired
	private ConsultaRepository consultaRepository;

	public ConsultaDTO marcarConsulta(ConsultaCreateDTO consultaForm)
			throws ClinicaUnavailableException, ConflictingScheduleException,
			EntityNotFoundException, EmptyListException {
		
		Long idMedico = consultaForm.idMedico();
		Long idPaciente = consultaForm.idPaciente();
		Long idEspecialidade = consultaForm.idEspecialidade();
		LocalDate data = consultaForm.data();
		LocalTime horario = consultaForm.horario().withNano(0);
		
		ConsultaValidator.validarRestricoesDeTempoMarcacao(data, horario);
		
		Long paciente = consultaValidator.validarPaciente(idPaciente, data);
		Long medico = consultaValidator.validarMedico(idMedico, data, horario, idEspecialidade);
		
		Consulta consulta = new Consulta(paciente, medico, data, horario);
		consultaRepository.save(consulta);
		return new ConsultaDTO(consulta, paciente, medico);
	}
		
	public void cancelarConsulta(ConsultaCancelDTO consultaForm, Long idConsulta)
			throws ConsultaNotFoundException, ConsultaCanceladaException, ConflictingScheduleException {
		
		Consulta consulta = consultaValidator.validarConsulta(idConsulta);
		ConsultaValidator.validarRestricoesDeTempoCancelamento(consulta.getData(), consulta.getHorario());
		MotivoCancelamento motivo = ConsultaValidator.validarMotivoCancelamento(consultaForm.motivo_cancelamento());
		
		consulta.setMotivoCancelamento(motivo);
		consultaRepository.save(consulta);
	}
}
