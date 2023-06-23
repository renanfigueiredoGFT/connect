package com.lagoinha.connect.controller;

import java.time.LocalDate;

import com.lagoinha.connect.model.start.Aluno;
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
	@RequestMapping(path = "/teste-csv", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> testarEnvioCSV(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.testarPlanilhaCSV(planilha));
	}
	
	@CrossOrigin
	@RequestMapping(path = "/teste-excel", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> testarEnvioExcel(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.testarPlanilhaExcel(planilha));
	}
	
	@CrossOrigin
	@RequestMapping(path = "/enviar-email-csv", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> enviarEmailCSV(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.importarPlanilhaCSV(planilha));
	}
	
	@CrossOrigin
	@RequestMapping(path = "/enviar-email-excel", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> enviarEmailExcel(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(certificadoService.importarPlanilhaExcel(planilha));
	}
	
	@PostMapping("enviar-email-individual")
	public ResponseEntity<?> enviarEmailIndividual(Aluno aluno){
		certificadoService.sendEmail(aluno, 1, LocalDate.now().getYear());
		return ResponseEntity.ok("Email enviado com sucesso!");
		
	}
	
}
