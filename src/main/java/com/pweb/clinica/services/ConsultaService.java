package com.pweb.clinica.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.exceptions.EmptyListException;
import com.pweb.clinica.exceptions.EspecialidadeNotFoundException;
import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Especialidade;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.ConsultaRepository;
import com.pweb.clinica.repositories.EspecialidadeRepository;
import com.pweb.clinica.repositories.MedicoRepository;
import com.pweb.clinica.repositories.PacienteRepository;

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
	
	private final int HORARIOS_DISPONIVEIS[] = {7, 8 , 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
	
	public Consulta marcarConsulta(Long idPaciente, Long idMedico, Long idEspecialidade)
			throws PacienteNotFoundException, MedicoNotFoundException, EspecialidadeNotFoundException, EmptyListException {
		
		System.out.println("\nMARCAR CONSULTA");
		
		Especialidade especialidade = especialidadeRepository.findById(idEspecialidade).orElseThrow(EspecialidadeNotFoundException::new);
		
		if(idMedico == null) {
			System.out.println("\nMEDICO NULL");
			idMedico = escolherMedico(idEspecialidade);
		}
		
		// verificarHorariosDisponiveis(idMedico);
		
		Consulta consulta = new Consulta();
		
		Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(PacienteNotFoundException::new);
		Medico medico = medicoRepository.findById(idMedico).orElseThrow(MedicoNotFoundException::new);
		
		System.out.println("\nNEW CONSULTA");
		// ConsultaValidator.emHorarioDeFuncionamento();
		consulta.setMedico(medico);
		consulta.setPaciente(paciente);
		consulta.setData(LocalDate.now());
		consulta.setHorario(LocalTime.now());
		consultaRepository.save(consulta);
		
		return consulta;
	}
	
	private Long escolherMedico(Long idEspecialidade) throws EmptyListException {
		List<Medico> medicosEsp = medicoRepository.findByEspecialidade_idAndAtivoTrueOrderByNomeAsc(idEspecialidade)
				.orElseThrow(() -> new EmptyListException("Nenhum médico disponível para esta especialidade"));
		
		Random rand = new Random();
		Long id = rand.nextLong(medicosEsp.size());
		System.out.println("\nESCOLHI O MEDICO DE ID: " + id);
		return id;
		// TODO: Validar se esse médico está disponível
	}
	
//	private List<LocalDateTime> verificarHorariosDisponiveis(Long idMedico) {
//		
//		return ;
//	}
//	
}
