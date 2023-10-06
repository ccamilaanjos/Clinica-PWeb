package com.pweb.clinica.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.dtos.ConsultaPostDTO;
import com.pweb.clinica.exceptions.ClinicaUnavailableException;
import com.pweb.clinica.exceptions.EmptyListException;
import com.pweb.clinica.exceptions.EspecialidadeNotFoundException;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.ConsultaRepository;
import com.pweb.clinica.repositories.EspecialidadeRepository;
import com.pweb.clinica.repositories.MedicoRepository;
import com.pweb.clinica.repositories.PacienteRepository;
import com.pweb.clinica.validators.ConsultaValidator;

@Service
public class ConsultaService {
	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private EspecialidadeRepository especialidadeRepository;
	
	// private final int HORARIOS_DISPONIVEIS[] = {7, 8 , 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
	
	public Consulta marcarConsulta(ConsultaPostDTO consultaForm)
			throws PacienteNotFoundException, MedicoNotFoundException, EspecialidadeNotFoundException,
			EmptyListException, ClinicaUnavailableException {
		
		Long idMedico = consultaForm.idMedico();
		Long idPaciente = consultaForm.idPaciente();
		Long idEspecialidade = consultaForm.idEspecialidade();
		LocalTime horario = ConsultaValidator.zerarNanos(consultaForm.horario());
		
		if(consultaForm.idMedico() == null) {
			System.out.println("\n\nID DO MEDICO NULL");
			// Caso o id do médico não seja enviado, buscar lista de médicos disponíveis para esta especialidade
			especialidadeRepository.findById(idEspecialidade).orElseThrow(EspecialidadeNotFoundException::new);
			idMedico = escolherMedico(idEspecialidade, consultaForm.data(), horario);
		}
		
		// TODO: Validar se médico e paciente estão ativos no sistema
		// TODO: Validar se paciente já tem consulta marcada no dia
		// TODO: Validar se o médico já possui outra consulta agendada na mesma data/hora
		Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(PacienteNotFoundException::new);
		Medico medico = medicoRepository.findById(idMedico).orElseThrow(MedicoNotFoundException::new);

		if(!ConsultaValidator.emHorarioDeFuncionamento(consultaForm.data(), horario)) {
			throw new ClinicaUnavailableException();
		}
		
		// TODO: Validar se a marcação foi realizada com ao menos 30 minutos de antecedência
		
		Consulta consulta = new Consulta();
		
		consulta.setMedico(medico);
		consulta.setPaciente(paciente);
		consulta.setData(consultaForm.data());
		consulta.setHorario(horario);
		consultaRepository.save(consulta);
		
		return consulta;
	}
	
	private Long escolherMedico(Long idEspecialidade, LocalDate data, LocalTime horario) throws EmptyListException {
		List<Medico> medicosEspecialistas = medicoRepository.findByEspecialidade_idAndAtivoTrueOrderByNomeAsc(idEspecialidade)
				.orElseThrow(() -> new EmptyListException("Nenhum médico disponível para esta especialidade"));
		
		Random rand = new Random();
		int escolhido = rand.nextInt(medicosEspecialistas.size());
		Medico medico = medicosEspecialistas.get(escolhido);
		
		// Valida se esse médico está disponível
		List<Consulta> consultasNesteHorario = consultaRepository.findByDataAndHorarioAndMedico_id(data, horario, medico.getId());
		if(consultasNesteHorario.isEmpty()) {
			return medico.getId();			
		}
		
		return escolherMedico(idEspecialidade, data, horario);
	}
	
}
