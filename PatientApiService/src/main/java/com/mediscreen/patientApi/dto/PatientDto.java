package com.mediscreen.patientApi.dto;

import java.time.LocalDate;

import com.mediscreen.patientApi.domain.Patient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

/**
 * PatientDTO	.
 *
 * @author Antoine Lanselle
 */
public class PatientDto {
	
	private int id;

	@Size(min = 2, max = 20)
	@NotNull(message="Enter a Lastname.")
	private String family;
	
	@Size(min = 2, max = 20)
	@NotNull(message="Enter a Firstname.")
	private String given;
	
	@Past
	@NotNull
	private LocalDate dob;
	
	@NotNull
	private String sex;
	
	private String address;
	
	private String phone;
	
	public PatientDto() {
		
	}
	
	public PatientDto(Patient patient) {
		this.id = patient.getId();
		this.family = patient.getLastname();
		this.given = patient.getFirstname();
		this.dob = patient.getDateOfBirth();
		this.sex = patient.getGender();
		this.address = patient.getAddress();
		this.phone = patient.getPhone();
	}
	
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
	
	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getGiven() {
		return given;
	}

	public void setGiven(String given) {
		this.given = given;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
