package com.pweb.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pweb.email.models.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {}
