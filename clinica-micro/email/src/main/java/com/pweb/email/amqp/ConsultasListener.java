package com.pweb.email.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.pweb.email.dtos.EmailDTO;
import com.pweb.email.services.EmailService;

@Component
public class ConsultasListener {
	@Autowired
	private EmailService emailService;
	
	@RabbitListener(queues = "emails.to-send")
    public void receberMensagem(@Payload EmailDTO email) {
        emailService.enviarEmail(email);
    }
}