package com.pweb.consulta.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pweb.consulta.enums.MotivoCancelamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="consultas")
public class Consulta {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private Long pacienteId;
	@Column(nullable=false)
	private Long medicoId;
	@Column(nullable=false)
	private LocalDate data;
	@Column(nullable=false)
	private LocalTime horario;
	@Enumerated(EnumType.STRING)
	@Column(name="motivo_cancelamento")
	private MotivoCancelamento motivoCancelamento;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(nullable=false)
	private LocalDateTime updatedAt;
	
	public Consulta() {}
	
	public Consulta(Long paciente, Long medico, LocalDate data, LocalTime horario) {
		this.pacienteId = paciente;
		this.medicoId = medico;
		this.data = data;
		this.horario = horario;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPaciente() {
		return pacienteId;
	}
	
	public void setPaciente(Long paciente) {
		this.pacienteId = paciente;
	}
	
	public Long getMedico() {
		return medicoId;
	}
	
	public void setMedico(Long medico) {
		this.medicoId = medico;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public LocalTime getHorario() {
		return horario;
	}
	
	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
	
	public MotivoCancelamento getMotivoCancelamento() {
		return motivoCancelamento;
	}
	
	public void setMotivoCancelamento(MotivoCancelamento motivo) {
		this.motivoCancelamento = motivo;
	}
}
