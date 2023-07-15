package com.mediscreen.patientApi.domain;

import java.time.LocalDate;

import com.mediscreen.patientApi.dto.PatientDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Patient.
 *
 * @author Antoine Lanselle
 */
@Entity
@Table(name = "Patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "gender")
	private String gender;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;

	public Patient() {

	}

	public Patient(PatientDto patientDto) {
		this.firstname = patientDto.getGiven();
		this.lastname = patientDto.getFamily();
		this.dateOfBirth = patientDto.getDob();
		this.gender = patientDto.getSex();
		this.address = patientDto.getAddress();
		this.phone = patientDto.getPhone();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
