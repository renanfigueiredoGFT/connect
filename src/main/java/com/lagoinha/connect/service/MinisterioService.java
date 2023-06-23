package com.lagoinha.connect.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.model.voluntario.Ministerio;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MinisterioService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	private final static String COLLECTION = "ministerio";
	
	public Ministerio save(Ministerio ministerio) {
		return mongoTemplate.save(ministerio, COLLECTION);
	}
	
	public List<Ministerio> list(){
		return mongoTemplate.findAll(Ministerio.class, COLLECTION);
	}
	
	public Ministerio findById(String id) {
		return mongoTemplate.findById(id, Ministerio.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Ministerio.class, COLLECTION);
	}
	
	public Ministerio edit(Ministerio ministerio) {
		Query query  = new Query(Criteria.where("id").is(ministerio.getId()));
		Ministerio usuarioAuxiliar = mongoTemplate.findOne(query, Ministerio.class);
		if(usuarioAuxiliar != null) {
			return mongoTemplate.save(ministerio, COLLECTION);
		}
		return null;
	}
	
}
