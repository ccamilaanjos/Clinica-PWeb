package com.pweb.consulta.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pweb.consulta.clients.EmailDTO;
import com.pweb.consulta.clients.MedicoClient;
import com.pweb.consulta.clients.MedicoGetDTO;
import com.pweb.consulta.clients.PacienteClient;
import com.pweb.consulta.clients.PacienteGetDTO;
import com.pweb.consulta.dtos.ConsultaCancelDTO;
import com.pweb.consulta.dtos.ConsultaCreateDTO;
import com.pweb.consulta.dtos.ConsultaDTO;
import com.pweb.consulta.dtos.MedicoDTO;
import com.pweb.consulta.dtos.PacienteDTO;
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
	@Autowired
	private PacienteClient pacienteClient;
	@Autowired
	private MedicoClient medicoClient;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private final String MARCACAO = "Marcação de Consulta";
	private final String CANCELAMENTO = "Cancelamento de Consulta";	

	public ConsultaDTO marcarConsulta(ConsultaCreateDTO consultaForm)
			throws ClinicaUnavailableException, ConflictingScheduleException,
			EntityNotFoundException, EmptyListException {
		
		Long idMedico = consultaForm.idMedico();
		Long idPaciente = consultaForm.idPaciente();
		Long idEspecialidade = consultaForm.idEspecialidade();
		LocalDate data = consultaForm.data();
		LocalTime horario = consultaForm.horario().withNano(0);
		
		ConsultaValidator.validarRestricoesDeTempoMarcacao(data, horario);
		
		PacienteDTO paciente = consultaValidator.validarPaciente(idPaciente, data);
		MedicoDTO medico = consultaValidator.validarMedico(idMedico, data, horario, idEspecialidade);
				
		Consulta consulta = new Consulta(paciente.id(), medico.id(), data, horario);
		consultaRepository.save(consulta);
		
		enviarEmailAoPaciente(paciente.paciente(), medico.medico(), consulta, MARCACAO);
		return new ConsultaDTO(consulta, paciente.id(), medico.id());
	}
		
	public void cancelarConsulta(ConsultaCancelDTO consultaForm, Long idConsulta)
			throws ConsultaNotFoundException, ConsultaCanceladaException, ConflictingScheduleException {
		
		Consulta consulta = consultaValidator.validarConsulta(idConsulta);
		ConsultaValidator.validarRestricoesDeTempoCancelamento(consulta.getData(), consulta.getHorario());
		MotivoCancelamento motivo = ConsultaValidator.validarMotivoCancelamento(consultaForm.motivo_cancelamento());
		
		consulta.setMotivoCancelamento(motivo);
		consultaRepository.save(consulta);
		

		PacienteGetDTO paciente = pacienteClient.buscarAtivoPorId(consulta.getPaciente()).getBody();
		MedicoGetDTO medico = medicoClient.buscarAtivoPorId(consulta.getMedico()).getBody();
		enviarEmailAoPaciente(paciente, medico, consulta, CANCELAMENTO);
	}
	
	private void enviarEmailAoPaciente(PacienteGetDTO paciente, MedicoGetDTO medico, Consulta consulta, String subject) {	
		String mailTo = paciente.email();
		String mailFrom = "camila003hm@gmail.com";
		
		Locale locale = Locale.getDefault();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", locale);
        String date = consulta.getData().format(formatter);
        MotivoCancelamento motivoCancelamento = consulta.getMotivoCancelamento();
        String text = "";
        
		if (subject.equalsIgnoreCase(MARCACAO)) {
			text = setMensagemMarcacao(
					paciente.nome(),
					medico.nome(),
					date,
					consulta.getHorario(),
					medico.crm());
		}
		else if (subject.equalsIgnoreCase(CANCELAMENTO)) {
			text = setMensagemCancelamento(
					paciente.nome(),
					medico.nome(),
					date,
					consulta.getHorario(),
					medico.crm(),
					motivoCancelamento);
		}
		
		rabbitTemplate.convertAndSend("emails.to-send", new EmailDTO(mailFrom, mailTo, subject, new String(text)));
	}
	
	private String setMensagemMarcacao(
			String nomePaciente, String nomeMedico, String data, LocalTime horario, String crm) {		
		StringBuilder text = new StringBuilder();
		text.append("Olá, " + nomePaciente + "!\n");
		text.append("A sua consulta foi marcada com sucesso.\n\n");
		text.append("Data: " + data + "\n");
		text.append("Horário: " + horario + "\n");
		text.append("Dr(a). " + nomeMedico + ", CRM " + crm + "\n\n");
		text.append("Agradecemos pela preferência. :D\n");
		text.append("Enviado por CLINICA PWEB");
		
		return new String(text);
	}
	
	private String setMensagemCancelamento(
			String nomePaciente, String nomeMedico, String data, LocalTime horario, String crm, MotivoCancelamento motivoCancelamento) {
		StringBuilder text = new StringBuilder();
		text.append("Olá, " + nomePaciente + "!\n");
		text.append("A sua consulta com o(a) Dr(a) " + nomeMedico + " no dia " + data + " às " + horario + " foi desmarcada.\n");
		text.append("O motivo declarado foi: " + motivoCancelamento.toString() + "\n\n");
		text.append("Agradecemos pela preferência. :D\n");
		text.append("Enviado por CLINICA PWEB");
		
		return new String(text);
	}
}
