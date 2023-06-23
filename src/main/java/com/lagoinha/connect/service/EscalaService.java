package com.lagoinha.connect.service;

import java.util.ArrayList;
import java.util.List;

import com.lagoinha.connect.model.voluntario.Escala;
import com.lagoinha.connect.model.voluntario.EscalaVoluntario;
import com.lagoinha.connect.model.voluntario.Ministerio;
import com.lagoinha.connect.model.voluntario.Voluntario;
import com.lagoinha.connect.model.worship.ConnectBracelet;
import com.lagoinha.connect.model.worship.Worship;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class EscalaService {

	@Autowired
	MongoTemplate mongoTemplate;

	private final static String COLLECTION = "escala";

	public Escala save(Escala escala) {

		return mongoTemplate.save(escala, COLLECTION);
	}

	public List<Escala> list() {
		return mongoTemplate.findAll(Escala.class, COLLECTION);
	}

	public Escala findById(String id) {
		return mongoTemplate.findById(id, Escala.class, COLLECTION);
	}

	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Escala.class, COLLECTION);
	}

	public Escala edit(Escala escala) {
		Query query = new Query(Criteria.where("id").is(escala.getId()));
		Escala escalaAuxiliar = mongoTemplate.findOne(query, Escala.class);
		if (escalaAuxiliar != null) {
			return mongoTemplate.save(escala, COLLECTION);
		}
		return null;
	}

	public Escala addVoluntario(Escala escala, Voluntario voluntario, Ministerio ministerio, Boolean checkin) {
		EscalaVoluntario escalaVoluntario = new EscalaVoluntario();
		escalaVoluntario.setMinisterio(ministerio);
		escalaVoluntario.setVoluntario(voluntario);
		escalaVoluntario.setCheckin(checkin);

		List<EscalaVoluntario> escalaVoluntarioList = escala.getEscalaVoluntarioList();
		if (escalaVoluntarioList == null || escalaVoluntarioList.isEmpty()) {
			List<EscalaVoluntario> escalaVoluntarioListEmpty = new ArrayList<>();
			escalaVoluntarioListEmpty.add(escalaVoluntario);
			escala.setEscalaVoluntarioList(escalaVoluntarioListEmpty);
		} else {
			escalaVoluntarioList.add(escalaVoluntario);
		}

		Query query = new Query(Criteria.where("id").is(escala.getId()));
		Escala escalaAux = mongoTemplate.findOne(query, Escala.class);
		if (escalaAux != null) {
			return mongoTemplate.save(escala, COLLECTION);
		}
		return null;
	}

	public Escala fazerCheckin(Escala escala, Voluntario voluntario, Boolean chekin) {

		List<EscalaVoluntario> escalaVoluntarioList = escala.getEscalaVoluntarioList();
		for (EscalaVoluntario escalaVoluntario : escalaVoluntarioList) {
			if (escalaVoluntario.getVoluntario().getId().equals(voluntario.getId())) {
				escalaVoluntario.setCheckin(chekin);
			}
		}
		escala.setEscalaVoluntarioList(escalaVoluntarioList);

		Query query = new Query(Criteria.where("id").is(escala.getId()));
		Escala escalaAux = mongoTemplate.findOne(query, Escala.class);
		if (escalaAux != null) {
			return mongoTemplate.save(escala, COLLECTION);
		}
		return null;
	}
	

	public Boolean deleteVoluntario(String idEscala, String idVoluntario) {
		try {
			Query query = new Query(Criteria.where("id").is(idEscala));
			Escala escala = mongoTemplate.findOne(query, Escala.class);
			List<EscalaVoluntario> escalaVoluntarioList = escala.getEscalaVoluntarioList();
			for(EscalaVoluntario escalaVoluntario: escalaVoluntarioList) {
				if(escalaVoluntario.getVoluntario().getId().equals(idVoluntario)) {
					escalaVoluntarioList.remove(escalaVoluntario);
					break;
				}
			}
			escala.setEscalaVoluntarioList(escalaVoluntarioList);
			mongoTemplate.save(escala, COLLECTION);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
