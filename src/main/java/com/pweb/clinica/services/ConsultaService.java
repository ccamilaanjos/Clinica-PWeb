package com.pweb.clinica.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.ConsultaPostDTO;
import com.pweb.clinica.exceptions.ClinicaUnavailableException;
import com.pweb.clinica.exceptions.ConflictingScheduleException;
import com.pweb.clinica.exceptions.EmptyListException;
import com.pweb.clinica.exceptions.EspecialidadeNotFoundException;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.exceptions.PessoaInativaException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.validators.ConsultaValidator;

@Service
public class ConsultaService {

	public Consulta marcarConsulta(ConsultaPostDTO consultaForm)
			throws ClinicaUnavailableException, PacienteNotFoundException, ConflictingScheduleException,
			EspecialidadeNotFoundException, EmptyListException, MedicoNotFoundException,
			PessoaInativaException {
		
		Long idMedico = consultaForm.idMedico();
		Long idPaciente = consultaForm.idPaciente();
		Long idEspecialidade = consultaForm.idEspecialidade();
		LocalDate data = consultaForm.data();
		LocalTime horario = consultaForm.horario().withNano(0);
		ConsultaValidator validator = new ConsultaValidator();
		
		ConsultaValidator.validarRestricoesDeTempo(data, horario);
		
		Paciente paciente = validator.validarPaciente(idPaciente, data);
		Medico medico = validator.validarMedico(idMedico, data, horario, idEspecialidade);
		
		// TODO: Retornar DTO
		Consulta consulta = new Consulta(paciente, medico, data, horario);
		return consulta;
	}
		
	public Consulta cancelarConsulta() {
		return null;
	}
}
