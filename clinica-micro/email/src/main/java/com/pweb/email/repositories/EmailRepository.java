package com.pweb.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pweb.email.models.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {}
