package com.mediscreen.userInterface.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mediscreen.userInterface.beans.PatientBean;
import com.mediscreen.userInterface.proxies.PatientApiServiceProxy;

import jakarta.validation.Valid;

@Controller
public class ClientController {

	private final PatientApiServiceProxy patientProxy;

	public ClientController(PatientApiServiceProxy patientProxy) {
		this.patientProxy = patientProxy;
	}

	@GetMapping("/patient")
	public String patientListPage(Model model) {
		List<PatientBean> patients = patientProxy.getAllPatients();
		model.addAttribute("patients", patients);
		return "PatientList";
	}

	@GetMapping("/patient/add")
	public String patientAddPage(Model model) {
		model.addAttribute("patientBean", new PatientBean());
		return "PatientAdd";
	}
	
	@GetMapping("/patient/{id}")
	public String patientDetailsPage(Model model, @PathVariable("id") int id) {
		model.addAttribute("patientBean", patientProxy.getPatient(id));
		return "PatientDetails";
	}

	@PostMapping("/patient/add")
	public String patientAdd(@Valid @ModelAttribute("patientBean") PatientBean patientBean, BindingResult result) {
		if (result.hasErrors()) {
			return "PatientAdd";
		} else {
			patientProxy.addPatient(patientBean);
			return "redirect:/patient?addSuccess";
		}
	}
	
	@PostMapping("/patient/{id}")
	public String patientUpdate(@Valid @ModelAttribute("patientBean") PatientBean patientBean, BindingResult result,
			@PathVariable("id") int id) {
		if (result.hasErrors()) {
			return "redirect:/patient/" + id;
		} else {
			patientProxy.updatePatient(id, patientBean);
			return "redirect:/patient/" + id + "?success";
		}
	}

	@PostMapping("/patient/delete/{id}")
	public String patientDelete(Model model, @PathVariable("id") int id) {
		try {
			patientProxy.deletePatient(id);
			return "redirect:/patient?delSuccess";
		} catch (Exception ex) {
			return "redirect:/patient?delError";
		}
	}

}
