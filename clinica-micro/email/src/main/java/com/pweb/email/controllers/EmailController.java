package com.pweb.email.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pweb.email.dtos.EmailDTO;
import com.pweb.email.models.Email;
import com.pweb.email.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/send")
	public ResponseEntity<Email> enviarEmail(@RequestBody EmailDTO email) {
		return new ResponseEntity<Email>(emailService.enviarEmail(email), HttpStatus.CREATED);
	}
}