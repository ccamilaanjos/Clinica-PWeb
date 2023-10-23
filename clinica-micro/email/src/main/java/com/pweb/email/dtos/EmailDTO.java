package com.pweb.email.dtos;

import com.pweb.email.models.Email;

public record EmailDTO(String mailFrom, String mailTo, String mailSubject, String mailText) {
	
	public EmailDTO(Email email) {
		this(email.getMailFrom(), email.getMailTo(), email.getMailSubject(), email.getMailText());
	}
}
