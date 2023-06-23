package com.lagoinha.connect.model.voluntario;

import java.util.List;

import com.lagoinha.connect.model.worship.Worship;

import org.springframework.data.annotation.Id;

public class Escala {

	@Id
	private String id;
	private String idCulto;
	private List<EscalaVoluntario> escalaVoluntarioList;
	
	
	public String getIdCulto() {
		return idCulto;
	}
	public void setIdCulto(String idCulto) {
		this.idCulto = idCulto;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<EscalaVoluntario> getEscalaVoluntarioList() {
		return escalaVoluntarioList;
	}
	public void setEscalaVoluntarioList(List<EscalaVoluntario> escalaVoluntarioList) {
		this.escalaVoluntarioList = escalaVoluntarioList;
	}

	
	
	
	
}
