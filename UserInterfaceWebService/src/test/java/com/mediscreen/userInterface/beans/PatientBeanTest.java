package com.mediscreen.userInterface.beans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PatientBeanTest {

	 private Validator validator;

	    public PatientBeanTest() {
	        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        validator = factory.getValidator();
	    }

	    @Test
	    public void givenValidPatientBean_WhenValidating_ThenNoConstraintViolations() {
	        // GIVEN
	        PatientBean patientBean = new PatientBean();
	        patientBean.setFamily("Smith");
	        patientBean.setGiven("John");
	        patientBean.setDob("1952-02-21");
	        patientBean.setSex("M");
	        patientBean.setAddress("123 Main St");
	        patientBean.setPhone("555-789-1234");

	        // WHEN
	        int constraintViolations = validator.validate(patientBean).size();

	        // THEN
	        assertEquals(0, constraintViolations);
	    }

	    @Test
	    public void givenInvalidPatientBean_WhenValidating_ThenConstraintViolations() {
	        // GIVEN
	        PatientBean patientBean = new PatientBean();
	        patientBean.setFamily("S");
	        patientBean.setGiven("J");
	        patientBean.setDob(LocalDate.now().plusDays(1).toString());
	        patientBean.setSex("X");
	        patientBean.setAddress("Invalid&^$Address");
	        patientBean.setPhone("123456789");

	        // WHEN
	        int constraintViolations = validator.validate(patientBean).size();

	        // THEN
	        assertEquals(6, constraintViolations);
	    }
	
}
