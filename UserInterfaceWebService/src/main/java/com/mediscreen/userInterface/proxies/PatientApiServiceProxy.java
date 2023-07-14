package com.mediscreen.userInterface.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mediscreen.userInterface.beans.PatientBean;


@FeignClient(name = "PatientApiService", url = "localhost:8081")
public interface PatientApiServiceProxy {

	@GetMapping("/patient")
	public List<PatientBean> getAllPatients();

	@GetMapping("/patient/{id}")
	public PatientBean getPatient(@PathVariable("id") int patientId);
	
	@PostMapping("/patient/add")
	public String addPatient(@RequestBody PatientBean patientDto);

	@PutMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") int patientId, @RequestBody PatientBean patientBean);

	@DeleteMapping("/patient/delete/{id}")
	public String deletePatient(@PathVariable("id") int patientId);
}
