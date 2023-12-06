package com.pweb.consulta.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pweb.consulta.clients.EmailDTO;
import com.pweb.consulta.clients.EspecialidadeDTO;
import com.pweb.consulta.clients.MedicoClient;
import com.pweb.consulta.clients.MedicoConsultaDTO;
import com.pweb.consulta.clients.PacienteClient;
import com.pweb.consulta.clients.PacienteConsultaDTO;
import com.pweb.consulta.dtos.ConsultaCancelDTO;
import com.pweb.consulta.dtos.ConsultaCreateDTO;
import com.pweb.consulta.dtos.ConsultaDTO;
import com.pweb.consulta.dtos.ConsultaGetDTO;
import com.pweb.consulta.dtos.MedicoGetDTO;
import com.pweb.consulta.dtos.PacienteGetDTO;
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

	public ConsultaDTO marcarConsulta(ConsultaCreateDTO consultaForm) throws ClinicaUnavailableException,
			ConflictingScheduleException, EntityNotFoundException, EmptyListException {

		String crm = consultaForm.crm();
		String cpf = consultaForm.cpf();
		String especialidade = consultaForm.especialidade();
		LocalDate data = consultaForm.data();
		LocalTime horario = consultaForm.horario().withNano(0);

		ConsultaValidator.validarRestricoesDeTempoMarcacao(data, horario);

		PacienteConsultaDTO paciente = consultaValidator.validarPaciente(cpf, data);
		MedicoConsultaDTO medico = consultaValidator.validarMedico(crm, data, horario, especialidade);

		Consulta consulta = new Consulta(paciente.id(), medico.id(), data, horario);
		consultaRepository.save(consulta);

		enviarEmailAoPaciente(paciente, medico, consulta, MARCACAO);
		return new ConsultaDTO(consulta, paciente.id(), medico.id());
	}

	public void cancelarConsulta(ConsultaCancelDTO consultaForm, Long idConsulta)
			throws ConsultaNotFoundException, ConsultaCanceladaException, ConflictingScheduleException {

		Consulta consulta = consultaValidator.validarConsulta(idConsulta);
		ConsultaValidator.validarRestricoesDeTempoCancelamento(consulta.getData(), consulta.getHorario());
		MotivoCancelamento motivo = ConsultaValidator.validarMotivoCancelamento(consultaForm.motivo_cancelamento());

		consulta.setMotivoCancelamento(motivo);
		consultaRepository.save(consulta);

		PacienteConsultaDTO paciente = pacienteClient.buscarAtivoPorId(consulta.getPaciente()).getBody();
		MedicoConsultaDTO medico = medicoClient.buscarAtivoPorId(consulta.getMedico()).getBody();
		enviarEmailAoPaciente(paciente, medico, consulta, CANCELAMENTO);
	}

	private void enviarEmailAoPaciente(PacienteConsultaDTO paciente, MedicoConsultaDTO medico, Consulta consulta,
			String subject) {
		String mailTo = paciente.email();
		String mailFrom = "camila003hm@gmail.com";

		Locale locale = Locale.getDefault();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", locale);
		String date = consulta.getData().format(formatter);
		MotivoCancelamento motivoCancelamento = consulta.getMotivoCancelamento();
		String text = "";

		if (subject.equalsIgnoreCase(MARCACAO)) {
			text = setMensagemMarcacao(paciente.nome(), medico.nome(), date, consulta.getHorario(), medico.crm());
		} else if (subject.equalsIgnoreCase(CANCELAMENTO)) {
			text = setMensagemCancelamento(paciente.nome(), medico.nome(), date, consulta.getHorario(), medico.crm(),
					motivoCancelamento);
		}

		rabbitTemplate.convertAndSend("emails.to-send", new EmailDTO(mailFrom, mailTo, subject, new String(text)));
	}

	private String setMensagemMarcacao(String nomePaciente, String nomeMedico, String data, LocalTime horario,
			String crm) {
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

	private String setMensagemCancelamento(String nomePaciente, String nomeMedico, String data, LocalTime horario,
			String crm, MotivoCancelamento motivoCancelamento) {
		StringBuilder text = new StringBuilder();
		text.append("Olá, " + nomePaciente + "!\n");
		text.append("A sua consulta com o(a) Dr(a) " + nomeMedico + " no dia " + data + " às " + horario
				+ " foi desmarcada.\n");
		text.append("O motivo declarado foi: " + motivoCancelamento.toString() + "\n\n");
		text.append("Agradecemos pela preferência. :D\n");
		text.append("Enviado por CLINICA PWEB");

		return new String(text);
	}

	public Page<ConsultaGetDTO> getTodas(Pageable pageable) {
		return consultaRepository.findAll(pageable).map(consulta -> {
			PacienteGetDTO paciente = new PacienteGetDTO(pacienteClient.buscarAtivoPorId(consulta.getPaciente()).getBody());
			MedicoConsultaDTO medicoConsulta = medicoClient.buscarAtivoPorId(consulta.getMedico()).getBody();
			EspecialidadeDTO especialidade = medicoClient.buscarEspecialidade(medicoConsulta.especialidade()).getBody();
			MedicoGetDTO medico = new MedicoGetDTO(medicoConsulta, especialidade);
			
			return new ConsultaGetDTO(consulta, paciente, medico);
		});
	}

	public ConsultaGetDTO encontrarPorId(Long id) throws ConsultaNotFoundException {
		Consulta consulta = consultaRepository.findById(id).orElseThrow(ConsultaNotFoundException::new);
		
		PacienteGetDTO paciente = new PacienteGetDTO(pacienteClient.buscarAtivoPorId(consulta.getPaciente()).getBody());
		MedicoConsultaDTO medicoConsulta = medicoClient.buscarAtivoPorId(consulta.getMedico()).getBody();
		EspecialidadeDTO especialidade = medicoClient.buscarEspecialidade(medicoConsulta.especialidade()).getBody();
		MedicoGetDTO medico = new MedicoGetDTO(medicoConsulta, especialidade);
		
		return new ConsultaGetDTO(consulta, paciente, medico);
	}
}
