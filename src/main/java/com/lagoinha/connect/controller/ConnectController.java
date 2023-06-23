package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.service.ConnectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("connect")
public class ConnectController {

	@Autowired
	ConnectService connectService;


	@GetMapping("readCSV")
	public String readCSV() {
		if (connectService.readCsv()) {
			return "Deu certo";
		} else {
			return "Deu errado";
		}
	}

	@GetMapping("signup")
	public String showSignUpForm(Connect connect) {
		return "connect/add-connect";
	}

	@GetMapping("index")
	public String showConnectList(Model model) {
		model.addAttribute("connects", connectService.list());
		return "connect/index";
	}

	@PostMapping("addconnect")
	public String save(Connect connect, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/connect/add-connect";
		}

		connectService.save(connect);
		return "redirect:/connect/index";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		Connect connect = connectService.findById(id);
		model.addAttribute("connect", connect);
		return "connect/update-connect";
	}

	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Connect connect, BindingResult result, Model model) {
		if (result.hasErrors()) {
			connect.setId(connect.getId());
			return "connect/update-connect";
		}
		connectService.edit(connect);
		return "redirect:/connect/index";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		connectService.delete(id);
		return "redirect:/connect/index";
	}
	
	@GetMapping("deleteWrongAge")
	public String deleteWrongAge() {
		connectService.deleteWrongAge();
		return "redirect:/connect/index";
	}
	
	@GetMapping("deleteDuplicate")
	public String deleteDuplicate() {
		connectService.deleteDuplicate();
		return "redirect:/connect/index";
	}

}
