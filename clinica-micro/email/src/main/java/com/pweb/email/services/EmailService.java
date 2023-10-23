package com.pweb.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pweb.email.dtos.EmailDTO;
import com.pweb.email.models.Email;
import com.pweb.email.models.EmailStatus;
import com.pweb.email.repositories.EmailRepository;

@Service
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private JavaMailSender emailSender;

	public Email enviarEmail(EmailDTO emailDto) {
		Email data = new Email(emailDto);
		data.setSendDateEmail(LocalDateTime.now());
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(emailDto.mailFrom());
		message.setTo(emailDto.mailTo());
		message.setSubject(emailDto.mailSubject());
		message.setText(emailDto.mailText());
		data.setStatus(EmailStatus.SENT);
		
		emailSender.send(message);
		emailRepository.save(data);
		return data;
	}
}
