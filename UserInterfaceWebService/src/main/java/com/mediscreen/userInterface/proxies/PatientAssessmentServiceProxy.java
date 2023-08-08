package com.mediscreen.userInterface.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PatientAssessmentServiceProxy is a Feign client interface for making calls to
 * the Patient Assessment service. It defines the endpoints and HTTP methods to
 * get assessment related operations.
 * 
 * @author Antoine Lanselle
 */
@FeignClient(name = "PatientAssessmentService", url = "${patient.assessment.url}")
public interface PatientAssessmentServiceProxy {

	@PostMapping("/assess/id")
	public String assessmentByIdJson(@RequestParam int patId);

}
