package com.mediscreen.PatientAssesmentService.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediscreen.PatientAssesmentService.beans.NotesPatientBean;

/**
 * NotesPatientServiceProxy is a Feign client interface for making API calls to
 * the Notes Patient API service. It defines the endpoints and HTTP methods to
 * interact with the notes related operations.
 * 
 * @author Antoine Lanselle
 */
@FeignClient(name = "NotesPatientService", url = "${notes.patient.url}")
public interface NotesPatientServiceProxy {

	@GetMapping("/patHistory")
	public List<NotesPatientBean> getAllPatientNotes(@RequestParam int patId);

}