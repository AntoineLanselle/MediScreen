package com.mediscreen.userInterface.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class PatientBean {

	private int id;

	@Pattern(regexp = "^[a-zA-Z]{2,20}$", message = "Lastname must be less than 21 letters.")
	private String family;

	@Pattern(regexp = "^[a-zA-Z]{2,20}$", message = "Firstname must be less than 21 letters.")
	private String given;

	@Past(message = "Date Of Birth is not valid.")
	private LocalDate dob;

	@Pattern(regexp = "^[F|H]{1}$", message = "Gender must be H or F.")
	private String sex;

	@Pattern(regexp = "^([\\w\\s]{2,255}|)$", message = "Address must be alphanumeric characters.")
	private String address;

	@Pattern(regexp = "^((\\d{3}\\-){2}\\d{4}|)$", message = "Phone number must be xxx-xxx-xxxx.")
	private String phone;

	public PatientBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public void setDob(String dob) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.dob = LocalDate.parse(dob, formatter);
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
