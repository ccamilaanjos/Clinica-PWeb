package com.pweb.clinica.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pweb.clinica.enums.MotivoCancelamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="consultas")
public class Consulta {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="paciente_id")
	private Paciente paciente;
	@ManyToOne
	@JoinColumn(name="medico_id")
	private Medico medico;
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
	
	public Consulta(Paciente paciente, Medico medico, LocalDate data, LocalTime horario) {
		this.paciente = paciente;
		this.medico = medico;
		this.data = data;
		this.horario = horario;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public Medico getMedico() {
		return medico;
	}
	
	public void setMedico(Medico medico) {
		this.medico = medico;
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
