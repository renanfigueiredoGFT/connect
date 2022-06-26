package com.lagoinha.connect.service;

import java.util.List;

import com.lagoinha.connect.model.ConnectBracelet;
import com.lagoinha.connect.model.Worship;
import com.lagoinha.connect.util.Criptografia;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class WorshipService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	Criptografia criptografia;
	
	private final static String COLLECTION = "worship";
	
	public Worship save(Worship worship) {
		//usuario.setSenha(criptografia.criptografia(worship.getSenha()));
		return mongoTemplate.save(worship, COLLECTION);
	}
	
	public List<Worship> list(){
		return mongoTemplate.findAll(Worship.class, COLLECTION);
	}
	
	public Worship findById(String id) {
		return mongoTemplate.findById(id, Worship.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Worship.class, COLLECTION);
	}
	
	public Worship edit(Worship usuario) {
		Query query  = new Query(Criteria.where("id").is(usuario.getId()));
		Worship usuarioAuxiliar = mongoTemplate.findOne(query, Worship.class);
		if(usuarioAuxiliar != null) {
			//usuario.setSenha(criptografia.criptografia(usuario.getSenha()));
			return mongoTemplate.save(usuario, COLLECTION);
		}
		return null;
	}

	public Boolean deleteConnect(String idWorship, String idConnect) {
		try {
			Query query = new Query(Criteria.where("id").is(idWorship));
			Worship worship = mongoTemplate.findOne(query, Worship.class);
			List<ConnectBracelet> connectBracelets = worship.getConnectBracelet();
			for(ConnectBracelet connectBracelet: connectBracelets) {
				if(connectBracelet.getConnect().getId().equals(idConnect)) {
					connectBracelets.remove(connectBracelet);
					break;
				}
			}
			worship.setConnectBracelet(connectBracelets);
			mongoTemplate.save(worship, COLLECTION);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
