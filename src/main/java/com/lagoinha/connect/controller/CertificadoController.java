package com.lagoinha.connect.controller;

import java.time.LocalDate;

import com.lagoinha.connect.model.Aluno;
import com.lagoinha.connect.service.CertificadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("certificado")
public class CertificadoController {

	@Autowired
	CertificadoService certificadoService;
	
	@CrossOrigin
	@RequestMapping(path = "/testar-planilha", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> testarPlanilha(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.testarPlanilha(planilha));
	}
	
	@PostMapping("importar-planilha")
	public ResponseEntity<?> importarPlanilha(){
		return ResponseEntity.ok(certificadoService.importarPlanilha());
	}
	
	@PostMapping("enviar-email")
	public ResponseEntity<?> criarEmail(Aluno aluno){
		certificadoService.sendEmail(aluno, 1, LocalDate.now().getYear());
		return ResponseEntity.ok("Email enviado com sucesso!");
		
	}
	
}
