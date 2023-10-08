package com.pweb.clinica.validators;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pweb.clinica.exceptions.ClinicaUnavailableException;
import com.pweb.clinica.exceptions.ConflictingScheduleException;
import com.pweb.clinica.exceptions.EmptyListException;
import com.pweb.clinica.exceptions.EspecialidadeNotFoundException;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.ConsultaRepository;
import com.pweb.clinica.services.EspecialidadeService;
import com.pweb.clinica.services.MedicoService;
import com.pweb.clinica.services.PacienteService;

@Component
public class ConsultaValidator {
	private final static int HORARIO_ABERTURA = 7;
	private final static int HORARIO_FECHAMENTO = 19;
	private final static DayOfWeek DIA_INICIAL = DayOfWeek.MONDAY;
	private final static DayOfWeek DIA_FINAL = DayOfWeek.SATURDAY;
	private final static Long MIN_ANTECEDENCIA_MARCACAO = (long) 30;
	
	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private MedicoService medicoService;
	@Autowired
	private EspecialidadeService especialidadeService;
	@Autowired
	private ConsultaRepository consultaRepository;
	
	// Verifica se a consulta foi marcada para um momento válido e foi realizada com ao menos 30 minutos de antecedência
	public static void validarRestricoesDeTempo(LocalDate data, LocalTime horario)
			throws ClinicaUnavailableException, ConflictingScheduleException {
		LocalDate hoje = LocalDate.now();
		LocalTime agora = LocalTime.now();
		
		if(!emHorarioDeFuncionamento(data, horario)) {
			throw new ClinicaUnavailableException("A clínica não está disponível para marcações no momento informado");
		}

		if(!emTempoDeMarcacao(data, horario, hoje, agora)) {
			throw new ClinicaUnavailableException("O agendamento precisa ocorrer com pelo menos 30 minutos de antecedência");
		}
	}
	
	/** 
	  * Verifica se o dia da semana é válido (de seg. à sábado).
	  * Verifica se o horário é maior ou igual ao horário de abertura ou menor ou igual ao horário
	  * de fechamento menos 1h (respeitando que cada consulta tem exatamente 1h de duração).
	  * Verifica se o horário mais 1h (duração da consulta) passa do horário de funcionamento da clínica.
	 */
	
	public static Boolean emHorarioDeFuncionamento(LocalDate data, LocalTime horario) {
		DayOfWeek dia = data.getDayOfWeek();
		
		if(dia.compareTo(DIA_INICIAL) < 0
			|| dia.compareTo(DIA_FINAL) > 0
			|| horario.getHour() < HORARIO_ABERTURA
			|| horario.getHour() > (HORARIO_FECHAMENTO - 1)
			|| horario.plusHours(1).isAfter(LocalTime.of(HORARIO_FECHAMENTO, 0, 0))) {
			return false;
		}
		return true;
	}
	
	/**
	  * Verifica se a consulta foi marcada com até 30 minutos de antecedência
	 */
	
	public static Boolean emTempoDeMarcacao(LocalDate data, LocalTime horario, LocalDate hoje, LocalTime agora) {
		if(data.equals(hoje) && agora.plusMinutes(MIN_ANTECEDENCIA_MARCACAO).isAfter(horario)) {
			return false;
		}
		return true;
	}
	
	public Paciente validarPaciente(Long idPaciente, LocalDate data) throws PacienteNotFoundException, ConflictingScheduleException {
		// Verifica se o paciente existe e está ativo
		Paciente paciente = pacienteService.buscarPacienteAtivo(idPaciente);
		
		// Verifica se o paciente já tem consulta marcada no dia
		if(!consultaRepository.findByDataAndPaciente_id(data, idPaciente).isEmpty()) {
			throw new ConflictingScheduleException("Paciente já tem consulta marcada para este dia");
		}
		
		return paciente;
	}
	
	public Medico validarMedico(Long idMedico, LocalDate data, LocalTime horario, Long idEspecialidade)
			throws ConflictingScheduleException, EspecialidadeNotFoundException, EmptyListException, MedicoNotFoundException {
		if(idMedico != null) {
			// Verifica se o médico existe e está ativo
			Medico medico = medicoService.buscarMedicoAtivo(idMedico);
			// Verifica se o médico está disponível neste horário e até que acabe a consulta (1h depois)
			if(!medicoEstaDisponivel(medico.getId(), data, horario)) {
				throw new ConflictingScheduleException("Médico indisponível neste horário");
			}
			return medico;
		}
	
		// Caso o id do médico não seja enviado, atribui um médico para esta consulta
		return atribuirMedicoParaConsulta(idEspecialidade, idMedico, data, horario);
	}

	private Boolean medicoEstaDisponivel(Long idMedico, LocalDate data, LocalTime horario) {
		List<Consulta> consultasNesteHorario = consultaRepository.verificarConsultasNesteHorario(
				idMedico, data, horario.minusHours(1), horario.plusHours(1));
		if(consultasNesteHorario.isEmpty()) {
			return true;
		}
		return false;
	}
	
	private Medico atribuirMedicoParaConsulta(Long idEspecialidade, Long idMedico, LocalDate data, LocalTime horario)
			throws EspecialidadeNotFoundException, EmptyListException {
		
		Especialidade especialidade = especialidadeService.buscarEspecialidade(idEspecialidade);
		List<Medico> medicosDisponiveis = medicoService.buscarMedicosDisponiveis(especialidade.getId(), idMedico, data, horario);

		if(medicosDisponiveis.isEmpty()) {
			throw new EmptyListException("Nenhum médico disponível para esta especialidade no horário e data fornecidos");
		}
		
		Random rand = new Random();
		int escolhido = rand.nextInt(medicosDisponiveis.size());
		
		Medico medico = medicosDisponiveis.get(escolhido);
		return medico;
	}
}
