package br.ifes.leds.sincap.web.model;

import java.io.Serializable;

public class UsuarioSessao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idUsuario;
	private Long idHospital;
	private String cpfUsuario;
	
	public String getCpfUsuario() {
		return cpfUsuario;
	}
	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdHospital() {
		return idHospital;
	}
	public void setIdHospital(Long idHospital) {
		this.idHospital = idHospital;
	}
	
}
