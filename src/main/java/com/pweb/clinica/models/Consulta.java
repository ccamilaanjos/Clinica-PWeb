package com.pweb.clinica.models;

import java.time.LocalDateTime;

public class Consulta {
	private Paciente paciente;
	private Medico medico;
	private LocalDateTime dataHora;
	
	public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora) {
		this.paciente = paciente;
		this.medico = medico;
		this.dataHora = dataHora;
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
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
}
