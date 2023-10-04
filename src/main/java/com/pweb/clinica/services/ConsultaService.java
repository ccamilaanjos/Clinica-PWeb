package com.pweb.clinica.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.clinica.exceptions.MedicoNotFoundException;
import com.pweb.clinica.exceptions.PacienteNotFoundException;
import com.pweb.clinica.models.Consulta;
import com.pweb.clinica.models.Medico;
import com.pweb.clinica.models.Paciente;
import com.pweb.clinica.repositories.ConsultaRepository;
import com.pweb.clinica.utils.validators.ConsultaValidator;

@Service
public class ConsultaService {
	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private MedicoService medicoService;
	@Autowired
	private PacienteService pacienteService;
	
	public Consulta marcarConsulta(Long idPaciente, Long idMedico) throws PacienteNotFoundException, MedicoNotFoundException {
		ConsultaValidator.emHorarioDeFuncionamento();
		Consulta consulta = new Consulta();
		
		Paciente paciente = pacienteService.buscarPorID(idPaciente).orElseThrow(PacienteNotFoundException::new);
		Medico medico = medicoService.buscarPorID(idMedico).orElseThrow(MedicoNotFoundException::new);
		
		return consulta;
	}
}
