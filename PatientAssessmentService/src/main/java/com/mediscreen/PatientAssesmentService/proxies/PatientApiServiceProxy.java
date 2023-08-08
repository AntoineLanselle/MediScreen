package com.mediscreen.PatientAssesmentService.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;

/**
 * PatientApiServiceProxy is a Feign client interface for making API calls to
 * the Patient API service. It defines the endpoints and HTTP methods to
 * interact with the patient related operations.
 * 
 * @author Antoine Lanselle
 */
@FeignClient(name = "PatientApiService", url = "${patient.api.url}")
public interface PatientApiServiceProxy {

	@GetMapping("/patient/search")
	public List<PatientBean> searchPatients(@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String lastname);

	@GetMapping("/patient/{id}")
	public PatientBean getPatient(@PathVariable("id") int patientId);

}
