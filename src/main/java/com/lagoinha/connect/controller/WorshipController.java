package com.lagoinha.connect.controller;

import java.util.ArrayList;
import java.util.List;

import com.lagoinha.connect.model.Connect;
import com.lagoinha.connect.model.ConnectBracelet;
import com.lagoinha.connect.model.Worship;
import com.lagoinha.connect.model.WorshipConnect;
import com.lagoinha.connect.service.ConnectService;
import com.lagoinha.connect.service.WorshipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("worship")
public class WorshipController {

	@Autowired
	WorshipService worshipService;
	
	@Autowired
	ConnectService connectService;
	
	@GetMapping("signup")
    public String showSignUpForm(Worship worship, Model model) {
		return "worship/add-worship";
    }
	
	@GetMapping("details/{id}")
    public String showDetails(@PathVariable("id") String id, Model model) {
	    Worship worship = worshipService.findById(id);
	    model.addAttribute("worship", worship);
	    return "worship/details";
    }
	
	@GetMapping("{id}/list-connect")
    public String listConnect(@PathVariable("id") String id, Model model) {
		List<Connect> connects = connectService.list();
	    Worship worship = worshipService.findById(id);
	    model.addAttribute("connects", connects);
	    model.addAttribute("worship", worship);
	    return "worship/list-connect";
    }
	
	@GetMapping("{idWorship}/add-connect/{idConnect}")
    public String insertConnect(@PathVariable("idWorship") String idWorship, 
    		@PathVariable("idConnect") String idConnect, Model model) {
		Connect connect = connectService.findById(idConnect);
	    Worship worship = worshipService.findById(idWorship);
	    model.addAttribute("worship", worship);
	    model.addAttribute("connect", connect);
	    model.addAttribute("worshipConnect", new WorshipConnect());
	    return "worship/add-connect";
    }
	
	@GetMapping("index")
	public String showWorshipList(Model model) {
	    model.addAttribute("worships", worshipService.list());
	    return "worship/index";
	}
	
	@PostMapping("addworship")
	public String save(Worship worship, BindingResult result, Model model){
		
		 if (result.hasErrors()) {
	            return "/worship/add-worship";
	        }
		
		worshipService.save(worship);
		return "redirect:/worship/index";
	}
	
	@PostMapping("add-connect-worship")
	public String addConnectWorhip(@ModelAttribute WorshipConnect worshipConnect, BindingResult result, Model model){
		Worship worship = worshipService.findById(worshipConnect.getWorshipId());
		Connect connect = connectService.findById(worshipConnect.getConnectId());
		Integer bracelet = worshipConnect.getBraceletNumber();
		ConnectBracelet connectBracelet = new ConnectBracelet();
		connectBracelet.setBracelet(bracelet);
		connectBracelet.setConnect(connect);
		List<ConnectBracelet> connectBracelets = worship.getConnectBracelet();
		if(connectBracelets == null || connectBracelets.isEmpty()) {
			List<ConnectBracelet> connectBraceletsEmpty = new ArrayList<>();
			connectBraceletsEmpty.add(connectBracelet);
			worship.setConnectBracelet(connectBraceletsEmpty);
		}else {
			connectBracelets.add(connectBracelet);
		}
		
		worshipService.edit(worship);
		
		return "redirect:/worship/details/" + worshipConnect.getWorshipId();
	}
	
	@GetMapping("connect/delete/{idWorship}/{idConnect}")
	public String deleteConnect(@PathVariable String idWorship, @PathVariable String idConnect, Model model){
		worshipService.deleteConnect(idWorship,idConnect);
		return "redirect:/worship/details/" + idWorship;
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    Worship worship = worshipService.findById(id);
	    model.addAttribute("worship", worship);
	    model.addAttribute("statusList", worship.getStatus());
	    return "worship/update-worship";
	}
	
	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Worship worship, BindingResult result, Model model){
		if (result.hasErrors()) {
			worship.setId(worship.getId());
	        return "worship/update-worship";
	    }
		worshipService.edit(worship);
		return "redirect:/worship/index";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model){
		worshipService.delete(id);
		return "redirect:/worship/index";
	}
	
}
