package com.pweb.consulta.validators;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pweb.consulta.clients.MedicoClient;
import com.pweb.consulta.clients.MedicoGetDTO;
import com.pweb.consulta.clients.PacienteClient;
import com.pweb.consulta.clients.PacienteGetDTO;
import com.pweb.consulta.enums.MotivoCancelamento;
import com.pweb.consulta.exceptions.ClinicaUnavailableException;
import com.pweb.consulta.exceptions.ConflictingScheduleException;
import com.pweb.consulta.exceptions.ConsultaCanceladaException;
import com.pweb.consulta.exceptions.ConsultaNotFoundException;
import com.pweb.consulta.exceptions.EmptyListException;
import com.pweb.consulta.exceptions.EntityNotFoundException;
import com.pweb.consulta.models.Consulta;
import com.pweb.consulta.repositories.ConsultaRepository;

@Component
public class ConsultaValidator {
	private final static int HORARIO_ABERTURA = 7;
	private final static int HORARIO_FECHAMENTO = 19;
	private final static DayOfWeek DIA_INICIAL = DayOfWeek.MONDAY;
	private final static DayOfWeek DIA_FINAL = DayOfWeek.SATURDAY;
	private final static long MIN_ANTECEDENCIA_MARCACAO = 30;
	private final static long H_ANTECEDENCIA_CANCELAMENTO = 24;
	
	@Autowired
	private PacienteClient pacienteClient;
	@Autowired
	private MedicoClient medicoClient;
	@Autowired
	private ConsultaRepository consultaRepository;
	
	public static void validarRestricoesDeTempoMarcacao(LocalDate data, LocalTime horario)
			throws ClinicaUnavailableException, ConflictingScheduleException {
		
		if(!emHorarioDeFuncionamento(data, horario)) {
			throw new ClinicaUnavailableException();
		}

		if(!emTempoDeMarcacao(data, horario)) {
			throw new ConflictingScheduleException("O agendamento precisa ocorrer com pelo menos 30 minutos de antecedência");
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
	  * Verifica se a consulta foi marcada com pelo menos 30 minutos de antecedência
	 */
	
	public static Boolean emTempoDeMarcacao(LocalDate data, LocalTime horario) {
		LocalDate hoje = LocalDate.now();
		LocalTime agora = LocalTime.now();
		
		if(data.equals(hoje) && agora.plusMinutes(MIN_ANTECEDENCIA_MARCACAO).isAfter(horario)) {
			return false;
		}
		return true;
	}
	
	public PacienteGetDTO validarPaciente(String cpf, LocalDate data) throws EntityNotFoundException, ConflictingScheduleException {
		// Verifica se o paciente existe e está ativo
		PacienteGetDTO paciente = pacienteClient.buscarAtivoPorCpf(cpf).getBody();
		
		// Verifica se o paciente já tem consulta marcada no dia
		if(!encontrarPorDataEPaciente(data, paciente.id()).isEmpty()) {
			throw new ConflictingScheduleException("Paciente já tem consulta marcada para este dia");
		}
		
		return paciente;
	}
	
	public MedicoGetDTO validarMedico(String crm, LocalDate data, LocalTime horario, String especialidade)
			throws ConflictingScheduleException, EntityNotFoundException, EmptyListException {
		if(crm != null) {
			// Verifica se o médico existe e está ativo
			MedicoGetDTO medico = medicoClient.buscarAtivoPorCrm(crm).getBody();
			// Verifica se o médico está disponível neste horário e até que acabe a consulta (1h depois)
			if(!medicoEstaDisponivel(medico.id(), data, horario)) {
				throw new ConflictingScheduleException("Médico indisponível neste horário");
			}
			
			return medico;
		}
	
		// Caso o id do médico não seja enviado, atribui um médico para esta consulta
		Long idMedico = atribuirMedicoParaConsulta(especialidade, data, horario);
		MedicoGetDTO medico = medicoClient.buscarAtivoPorId(idMedico).getBody();
		
		return medico;
	}

	/**
	  * Verifica se o médico está disponível no horário da consulta, com uma hora livre. Ou seja, se não
	  * possui consultas marcadas para menos que 1h antes ou para menos que 1h depois do horário fornecido.
	 */
	
	private Boolean medicoEstaDisponivel(Long idMedico, LocalDate data, LocalTime horario) {
		List<Consulta> consultasNesteHorario = encontrarConsultasMesmoHorario(idMedico, data, horario);
		
		if(consultasNesteHorario.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	  * Busca os médicos disponíveis no horário da consulta, com uma hora livre. Ou seja, que não possua
	  * consultas marcadas para menos que 1h antes ou para menos que 1h depois do horário fornecido, e
	  * escolhe um deles aleatoriamente.
	 */
	
	private Long atribuirMedicoParaConsulta(String especialidade, LocalDate data, LocalTime horario)
			throws EntityNotFoundException, EmptyListException {
		
		Long idEspecialidade = medicoClient.buscarEspecialidadePorNome(especialidade).getBody();
		List<Long> medicos = medicoClient.listarAtivosPorEspecialidade(idEspecialidade).getBody();
		List<Long> medicosOcupadosNesteHorario = consultaRepository.verificarMedicosOcupados(data, horario.minusHours(1), horario.plusHours(1));
		
		List<Long> medicosDisponiveis = medicos
				.stream()
                .filter(medico -> !medicosOcupadosNesteHorario.contains(medico))
                .collect(Collectors.toList());
		
		if(medicosDisponiveis.isEmpty()) {
			throw new EmptyListException("Nenhum médico disponível para esta especialidade no horário e data fornecidos");
		}
		
		Random rand = new Random();
		int escolhido = rand.nextInt(medicosDisponiveis.size());
		
		Long medico = medicosDisponiveis.get(escolhido);
		
		return medico;
	}
	
	/**
	  * Verifica se a consulta foi cancelada com pelo menos 24 horas de antecedência
	  * @throws ConflictingScheduleException 
	 */
	
	public static void validarRestricoesDeTempoCancelamento(LocalDate data, LocalTime horario)
			throws ConflictingScheduleException {
		
		if(!emTempoDeCancelamento(data, horario)) {
			throw new ConflictingScheduleException("O cancelamento precisa ocorrer com pelo menos 24 horas de antecedência");
		}
	}
	
	public Consulta validarConsulta(Long idConsulta) throws ConsultaNotFoundException, ConsultaCanceladaException {
		Consulta consulta = buscarConsulta(idConsulta);
		
		if(consulta.getMotivoCancelamento() != null) {
			throw new ConsultaCanceladaException(consulta.getMotivoCancelamento());
		}
		return consulta;
	}
	
	public static Boolean emTempoDeCancelamento(LocalDate data, LocalTime horario) {
		LocalDateTime dateTimeConsulta = LocalDateTime.of(data, horario);
		LocalDateTime hojeAgora = LocalDateTime.now();
		
		if(hojeAgora.plusHours(H_ANTECEDENCIA_CANCELAMENTO).isAfter(dateTimeConsulta)) {
			return false;
		}
		return true;
	}
	
	public Consulta buscarConsulta (Long idConsulta) throws ConsultaNotFoundException {
		return consultaRepository.findById(idConsulta).orElseThrow(ConsultaNotFoundException::new);
	}
	
	public List<Consulta> encontrarPorDataEPaciente(LocalDate data, Long idPaciente) {
		return consultaRepository.findByDataAndPacienteIdAndMotivoCancelamentoNull(data, idPaciente);
	}
	
	public List<Consulta> encontrarConsultasMesmoHorario(Long idMedico, LocalDate data, LocalTime horario) {
		return consultaRepository.verificarConsultasNesteHorario(
				idMedico, data, horario.minusHours(1), horario.plusHours(1));
	}

	public static MotivoCancelamento validarMotivoCancelamento(String motivo) {
		List<MotivoCancelamento> motivosPrevistos = MotivoCancelamento.obterMotivos();
		
		for(MotivoCancelamento m : motivosPrevistos) {
			if(m.name().equalsIgnoreCase(motivo)) {
				return m;
			}
		}
		return MotivoCancelamento.OUTROS;		
	}
}
