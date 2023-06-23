package com.lagoinha.connect.model.voluntario;

public class EscalaVoluntario {

	private Voluntario voluntario;
	private Ministerio ministerio;
	private Boolean checkin;
	
	public Voluntario getVoluntario() {
		return voluntario;
	}
	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}
	public Ministerio getMinisterio() {
		return ministerio;
	}
	public void setMinisterio(Ministerio ministerio) {
		this.ministerio = ministerio;
	}
	public Boolean getCheckin() {
		return checkin;
	}
	public void setCheckin(Boolean checkin) {
		this.checkin = checkin;
	}
	
	
}
