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
	private JavaMailSender javaMailSender;
	@Autowired
	private EmailRepository emailRepository;

	public Email enviarEmail(EmailDTO emailDto) {
		Email email = new Email(emailDto);
		email.setSendDateEmail(LocalDateTime.now());
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(emailDto.mailFrom());
		message.setTo(emailDto.mailTo());
		message.setSubject(emailDto.mailSubject());
		message.setText(emailDto.mailText());
		javaMailSender.send(message);
		
		email.setStatus(EmailStatus.SENT);
		emailRepository.save(email);
		return email;
	}
}
