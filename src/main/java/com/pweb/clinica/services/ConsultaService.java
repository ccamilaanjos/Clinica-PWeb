package com.pweb.clinica.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Consulta marcarConsulta(ConsultaPostDTO consultaForm)
			throws ClinicaUnavailableException, PacienteNotFoundException,
			ConflictingScheduleException, EspecialidadeNotFoundException,
			EmptyListException, MedicoNotFoundException,
			PessoaInativaException {
		
		Long idMedico = consultaForm.idMedico();
		Long idPaciente = consultaForm.idPaciente();
		Long idEspecialidade = consultaForm.idEspecialidade();
		LocalDate data = consultaForm.data();
		LocalTime horario = ConsultaValidator.zerarNanos(consultaForm.horario());
		
		// Verifica se consulta foi marcada para um momento válido
		if(!ConsultaValidator.emHorarioDeFuncionamento(data, horario)) {
			throw new ClinicaUnavailableException();
		}
		
		// Verifica se o paciente existe
		Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(PacienteNotFoundException::new);
		
		// Verifica se o paciente já tem consulta marcada no dia
		if(!consultaRepository.findByDataAndHorarioAndPaciente_id(data, horario, idPaciente).isEmpty()) {
			throw new ConflictingScheduleException("Paciente já tem consulta marcada para este dia");
		}
		
		// Verifica se o médico já possui outra consulta agendada na mesma data/horário
		if(!medicoEstaDisponivel(idMedico, data, horario)) {
			throw new ConflictingScheduleException("Médico possui outra consulta neste horário");
		}
		
		// Caso o id do médico não seja enviado, atribui um médico para esta consulta
		if(consultaForm.idMedico() == null) {
			especialidadeRepository.findById(idEspecialidade).orElseThrow(EspecialidadeNotFoundException::new);
			
			// Verifica se há quaisquer médicos disponíveis para esta especialidade
			List<Medico> medicosEspecialistas = getMedicosEspecialistas(idEspecialidade);
			if(medicosEspecialistas.isEmpty()) {
				throw new EmptyListException("Nenhum médico disponível para esta especialidade");
			}
			
			// Busca lista de médicos disponíveis para esta especialidade no mesmo horário e data
			idMedico = escolherMedico(medicosEspecialistas, consultaForm.data(), horario);				
		}
		
		// Verifica se o médico existe (DEAD CODE caso entre no if acima)
		Medico medico = medicoRepository.findById(idMedico).orElseThrow(MedicoNotFoundException::new);
		
		// Verifica se médico e paciente estão ativos no sistema
		if(paciente.getAtivo() == false || medico.getAtivo() == false) {
			throw new PessoaInativaException();
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
	
	private Long escolherMedico(List<Medico> medicosEspecialistas, LocalDate data, LocalTime horario) throws EmptyListException {
		
		// TODO: Criar array com indices disponíveis para evitar que o mesmo médico seja sorteado duas vezes
		Random rand = new Random();
		for(int i = 0; i < medicosEspecialistas.size(); i++) {
			int escolhido = rand.nextInt(medicosEspecialistas.size());
			Medico medico = medicosEspecialistas.get(escolhido);
			
			// Valida se esse médico está disponível
			if (medicoEstaDisponivel(medico.getId(), data, horario)) {
				return medico.getId();
			}
		}
		
		throw new EmptyListException("Nenhum médico disponível neste horário e data");
	}
	
	private List<Medico> getMedicosEspecialistas(Long idEspecialidade) throws EmptyListException {
		return medicoRepository.findByEspecialidade_idAndAtivoTrueOrderByNomeAsc(idEspecialidade)
				.orElseThrow(() -> new EmptyListException("Nenhum médico disponível para esta especialidade"));
	}
	
	private Boolean medicoEstaDisponivel(Long idMedico, LocalDate data, LocalTime horario) {
		List<Consulta> consultasNesteHorario = consultaRepository.findByDataAndHorarioAndMedico_id(data, horario, idMedico);
		if(consultasNesteHorario.isEmpty()) {
			return true;
		}
		return false;
	}
}
