package com.lagoinha.connect.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.lagoinha.connect.model.voluntario.Voluntario;
import com.mongodb.client.result.DeleteResult;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VoluntarioService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	private final static String COLLECTION = "voluntario";
	
	public Voluntario save(Voluntario voluntario) {
		if(voluntario.getName() != null) {
			voluntario.setName(voluntario.getName().toUpperCase());
		}
		return mongoTemplate.save(voluntario, COLLECTION);
	}
	
	public List<Voluntario> list(){
		return mongoTemplate.findAll(Voluntario.class, COLLECTION);
	}
	
	public Voluntario findById(String id) {
		return mongoTemplate.findById(id, Voluntario.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Voluntario.class, COLLECTION);
	}
	
	public Voluntario edit(Voluntario voluntario) {
		try {
			Query query  = new Query(Criteria.where("id").is(voluntario.getId()));
			Voluntario voluntarioAuxiliar = mongoTemplate.findOne(query, Voluntario.class);
			if(voluntarioAuxiliar != null) {
				if(voluntario.getName() != null) {
					voluntario.setName(voluntario.getName().toUpperCase());
				}
				return mongoTemplate.save(voluntario, COLLECTION);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private boolean isNotEmptyString(String string) {
	    return string != null && !string.isEmpty();
	}

	public List<Voluntario> importar(MultipartFile multipartFile) {
		List<Voluntario> voluntarios = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(multipartFile.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> allData = csvReader.readAll();
			int linha = 1;
			for (String[] row : allData) {
				try {
					if(isNotEmptyString(row[1]) && isNotEmptyString(row[2]) && isNotEmptyString(row[3])) {
						Voluntario voluntario = new Voluntario();
						voluntario.setName(row[1].toUpperCase() + " " + row[2].toUpperCase());
						voluntario.setPhone(row[3].toUpperCase());
						
						Query query  = new Query(Criteria.where("name").is(voluntario.getName()));
						
						if(mongoTemplate.exists(query, Voluntario.class, COLLECTION)) {
							System.out.println(linha + "Voluntário: "+voluntario.getName()+" já consta na base de dados");
						}else {
							if(voluntario.getName() != null) {
								voluntario.setName(voluntario.getName().toUpperCase());
							}
							mongoTemplate.save(voluntario, COLLECTION);
							voluntarios.add(voluntario);
						}
					}else {
						System.out.println(linha + " - nome e telefone são obrigatórios");
					}
					
					linha++;
				} catch (Exception e) {
					System.out.println(linha + " - " + e.getMessage());
					linha++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voluntarios;
	}
	
}
