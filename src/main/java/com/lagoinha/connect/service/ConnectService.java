package com.lagoinha.connect.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lagoinha.connect.model.Connect;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ConnectService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	private final static String COLLECTION = "connect";
	
	public Connect save(Connect user) {
		return mongoTemplate.save(user, COLLECTION);
	}
	
	public List<Connect> list(){
		return mongoTemplate.findAll(Connect.class, COLLECTION);
	}
	
	public Connect findById(String id) {
		return mongoTemplate.findById(id, Connect.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Connect.class, COLLECTION);
	}
	
	public Connect edit(Connect usuario) {
		Query query  = new Query(Criteria.where("id").is(usuario.getId()));
		Connect usuarioAuxiliar = mongoTemplate.findOne(query, Connect.class);
		if(usuarioAuxiliar != null) {
			return mongoTemplate.save(usuario, COLLECTION);
		}
		return null;
	}
	
	public Boolean readCsv() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("banco_connect.csv"));
			String line;
			while ((line = br.readLine()) != null) {
				try {
					String[] values = line.split(";");
					Connect connect = new Connect();
					connect.setName(values[0]);
					connect.setBirthDate(values[1]);
					connect.setResponsible(values[2]);
					connect.setPhone(values[3]);
					System.out.println(values[0]);
					mongoTemplate.save(connect, COLLECTION);
				} catch (Exception e) {
					System.out.println("Erro na linha: "+line);
				}
			}
			br.close();
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
	
}
