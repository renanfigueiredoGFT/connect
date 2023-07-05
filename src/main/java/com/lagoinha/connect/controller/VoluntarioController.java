package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.voluntario.Voluntario;
import com.lagoinha.connect.service.VoluntarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("voluntario")
public class VoluntarioController {

	@Autowired
	VoluntarioService voluntarioService;


	@GetMapping("signup")
	public String showSignUpForm(Voluntario voluntario) {
		return "voluntario/add-voluntario";
	}

	@GetMapping("index")
	public String showConnectList(Model model) {
		model.addAttribute("voluntarios", voluntarioService.list());
		return "voluntario/index";
	}

	@PostMapping("addvoluntario")
	public String save(Voluntario voluntario, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/voluntario/add-voluntario";
		}

		voluntarioService.save(voluntario);
		return "redirect:/voluntario/index";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		Voluntario voluntario = voluntarioService.findById(id);
		model.addAttribute("voluntario", voluntario);
		return "voluntario/update-voluntario";
	}

	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Voluntario voluntario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			voluntario.setId(voluntario.getId());
			return "voluntario/update-voluntario";
		}
		voluntarioService.edit(voluntario);
		return "redirect:/voluntario/index";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		voluntarioService.delete(id);
		return "redirect:/voluntario/index";
	}
	
	@CrossOrigin
	@RequestMapping(path = "/importar", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> importar(@RequestPart MultipartFile planilha){
		return ResponseEntity.ok(voluntarioService.importar(planilha));
	}

}
