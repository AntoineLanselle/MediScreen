package com.mediscreen.userInterface.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediscreen.userInterface.beans.PatientBean;
import com.mediscreen.userInterface.proxies.PatientApiServiceProxy;

import jakarta.validation.Valid;

/**
 * ClientController is a controller class that handles requests related to
 * patient management.
 * 
 * @author Antoine Lanselle
 */
@Controller
public class InterfaceController {

	private final PatientApiServiceProxy patientProxy;

	private Logger logger = LoggerFactory.getLogger(InterfaceController.class);

	public InterfaceController(PatientApiServiceProxy patientProxy) {
		this.patientProxy = patientProxy;
	}

	/**
	 * Handles a GET request to retrieve the patient list page.
	 *
	 * @param model     the Model object used to pass data to the view.
	 * @param firstname the optional firstname parameter for filtering patients by
	 *                  firstname.
	 * @param lastname  the optional lastname parameter for filtering patients by
	 *                  lastname.
	 * 
	 * @return the view name for the patient list page.
	 */
	@GetMapping("/patient")
	public String patientListPage(Model model, @RequestParam(required = false) String firstname,
			@RequestParam(required = false) String lastname) {
		logger.info("GET request - get patient list page");

		List<PatientBean> patients;
		if (firstname != null || lastname != null) {
			patients = patientProxy.searchPatients(firstname, lastname);
		} else {
			patients = patientProxy.getAllPatients();
		}
		model.addAttribute("patients", patients);
		return "PatientList";
	}

	/**
	 * Handles the GET request for the patient add page.
	 *
	 * @param model the model to be used for the view.
	 * 
	 * @return the view name for the patient add page.
	 */
	@GetMapping("/patient/add")
	public String patientAddPage(Model model) {
		logger.info("GET request - get patient add page");

		model.addAttribute("patientBean", new PatientBean());
		return "PatientAdd";
	}

	/**
	 * Handles the GET request for the patient details page.
	 *
	 * @param model the model to be used for the view.
	 * @param id    the ID of the patient to be displayed.
	 * 
	 * @return the view name for the patient details page.
	 */
	@GetMapping("/patient/{id}")
	public String patientDetailsPage(Model model, @PathVariable("id") int id) {
		logger.info("GET request - get patient details page");

		model.addAttribute("patientBean", patientProxy.getPatient(id));
		return "PatientDetails";
	}

	/**
	 * Handles the POST request for adding a patient.
	 *
	 * @param patientBean the PatientBean object containing the patient data.
	 * @param result      the binding result for data validation.
	 * 
	 * @return the view name to redirect after adding the patient.
	 */
	@PostMapping("/patient/add")
	public String patientAdd(@Valid @ModelAttribute("patientBean") PatientBean patientBean, BindingResult result) {
		logger.info("POST request - add patient" + patientBean.getGiven() + ", " + patientBean.getFamily());

		if (result.hasErrors()) {
			return "PatientAdd";
		} else {
			patientProxy.addPatientJson(patientBean);
			return "redirect:/patient?addSuccess";
		}
	}

	/**
	 * Handles the POST request for updating a patient.
	 *
	 * @param patientBean the PatientBean object containing the updated patient
	 *                    data.
	 * @param result      the binding result for data validation.
	 * @param id          the ID of the patient to be updated.
	 * 
	 * @return the view name to redirect after updating the patient.
	 */
	@PostMapping("/patient/{id}")
	public String patientUpdate(@Valid @ModelAttribute("patientBean") PatientBean patientBean, BindingResult result,
			@PathVariable("id") int id) {
		logger.info("POST request - update patient" + id);

		if (result.hasErrors()) {
			return "PatientDetails";
		} else {
			patientProxy.updatePatient(id, patientBean);
			return "redirect:/patient/" + id + "?success";
		}
	}

	/**
	 * Handles the POST request for deleting a patient.
	 *
	 * @param model the model to be used for the view.
	 * @param id    the ID of the patient to be deleted.
	 * 
	 * @return the view name to redirect after deleting the patient.
	 */
	@PostMapping("/patient/delete/{id}")
	public String patientDelete(Model model, @PathVariable("id") int id) {
		logger.info("POST request - delete patient" + id);

		try {
			patientProxy.deletePatient(id);
			return "redirect:/patient?delSuccess";
		} catch (Exception ex) {
			return "redirect:/patient?delError";
		}
	}

}
