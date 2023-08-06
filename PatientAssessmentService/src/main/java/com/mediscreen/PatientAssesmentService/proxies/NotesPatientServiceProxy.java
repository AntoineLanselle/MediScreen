package com.mediscreen.PatientAssesmentService.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/patHistory/{notesId}")
	public NotesPatientBean getNotesById(@PathVariable("notesId") int notesId);

	@GetMapping("/patHistory")
	public List<NotesPatientBean> getAllPatientNotes(@RequestParam int patId);

	@PostMapping(path = "/patHistory/add")
	public String addNotesJson(@RequestBody NotesPatientBean notesPatientBean);

	@PutMapping("/patHistory/update")
	public String updateNotes(@RequestBody NotesPatientBean NotesPatientBean);

	@DeleteMapping("/patHistory/delete/{notesId}")
	public String deleteNotes(@PathVariable("notesId") int notesId);

}