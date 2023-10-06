package com.pweb.clinica.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
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
	
	public Consulta() {}
	
	public Consulta(Paciente paciente, Medico medico, LocalDate data, LocalTime horario) {
		this.paciente = paciente;
		this.medico = medico;
		this.data = data;
		this.horario = horario;
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
}
