package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.voluntario.Ministerio;
import com.lagoinha.connect.service.MinisterioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ministerio")
public class MinisterioController {

	@Autowired
	MinisterioService ministerioService;


	@GetMapping("signup")
	public String showSignUpForm(Ministerio ministerio) {
		return "ministerio/add-ministerio";
	}

	@GetMapping("index")
	public String showConnectList(Model model) {
		model.addAttribute("ministerios", ministerioService.list());
		return "ministerio/index";
	}

	@PostMapping("addministerio")
	public String save(Ministerio ministerio, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/ministerio/add-ministerio";
		}

		ministerioService.save(ministerio);
		return "redirect:/ministerio/index";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		Ministerio ministerio = ministerioService.findById(id);
		model.addAttribute("ministerio", ministerio);
		return "ministerio/update-ministerio";
	}

	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Ministerio ministerio, BindingResult result, Model model) {
		if (result.hasErrors()) {
			ministerio.setId(ministerio.getId());
			return "ministerio/update-ministerio";
		}
		ministerioService.edit(ministerio);
		return "redirect:/ministerio/index";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		ministerioService.delete(id);
		return "redirect:/ministerio/index";
	}

}
