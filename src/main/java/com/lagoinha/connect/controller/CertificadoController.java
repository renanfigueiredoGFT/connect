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

import io.swagger.annotations.Api;

@Api(tags = "Certificado")
@RestController
@RequestMapping("certificado")
public class CertificadoController {

	@Autowired
	CertificadoService certificadoService;
	
	@CrossOrigin
	@RequestMapping(path = "/enviar-email-teste", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> testarEnvio(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.testarPlanilha(planilha));
	}
	
	@CrossOrigin
	@RequestMapping(path = "/enviar-email-arquivo", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> enviarEmailArquivo(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.importarPlanilha(planilha));
	}
	
	@PostMapping("enviar-email-individual")
	public ResponseEntity<?> enviarEmailIndividual(Aluno aluno){
		certificadoService.sendEmail(aluno, 1, LocalDate.now().getYear());
		return ResponseEntity.ok("Email enviado com sucesso!");
		
	}
	
}
