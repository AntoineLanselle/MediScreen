package com.mediscreen.NotesPatientService.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.NotesPatientService.dto.NotesDto;
import com.mediscreen.NotesPatientService.exception.NotesNotFoundException;
import com.mediscreen.NotesPatientService.service.NotesService;

class NotesControllerTest {

	@Mock
	private NotesService notesService;

	@InjectMocks
	private NotesController notesController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getNotesById_ExistingId_ShouldReturnNotesDto() {
		// GIVEN
		int notesId = 1;
		NotesDto notesDto = new NotesDto();
		when(notesService.getNotesById(notesId)).thenReturn(notesDto);

		// WHEN
		ResponseEntity<NotesDto> response = notesController.getNotesById(notesId);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(notesDto, response.getBody());
	}

	@Test
	public void getNotesById_NonExistingId_ShouldReturnNotFoundResponse() {
		// GIVEN
		int nonExistingId = 999;
		when(notesService.getNotesById(nonExistingId)).thenThrow(NotesNotFoundException.class);

		// WHEN
		ResponseEntity<NotesDto> response = notesController.getNotesById(nonExistingId);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void getAllPatientNotes_ShouldReturnListOfNotesDto() {
		// GIVEN
		int patientId = 123;
		List<NotesDto> notesList = new ArrayList<>();
		notesList.add(new NotesDto());
		when(notesService.findByPatientId(patientId)).thenReturn(notesList);

		// WHEN
		ResponseEntity<List<NotesDto>> response = notesController.getAllPatientNotes(patientId);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(notesList, response.getBody());
	}

	@Test
	public void addNotesJson_ShouldReturnCreatedResponse() {
		// GIVEN
		NotesDto notesDto = new NotesDto();

		// WHEN
		ResponseEntity<String> response = notesController.addNotesJson(notesDto);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Notes has been added in the data base.", response.getBody());
		verify(notesService).createNotes(notesDto);
	}

	@Test
	public void addNotesUrl_ShouldReturnCreatedResponse() {
		// GIVEN
		NotesDto notesDto = new NotesDto();

		// WHEN
		ResponseEntity<String> response = notesController.addNotesUrl(notesDto);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Notes has been added in the data base.", response.getBody());
		verify(notesService).createNotes(notesDto);
	}

	@Test
	public void updateNotes_ExistingId_ShouldReturnOkResponse() {
		// GIVEN
		int notesId = 1;
		NotesDto notesDto = new NotesDto();

		// WHEN
		ResponseEntity<String> response = notesController.updateNotes(notesId, notesDto);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Notes has been updated in data base.", response.getBody());
		verify(notesService).updateNotes(notesId, notesDto);
	}

	@Test
	public void updateNotes_NonExistingId_ShouldReturnNotFoundResponse() {
		// GIVEN
		int nonExistingId = 999;
		NotesDto notesDto = new NotesDto();
		doThrow(NotesNotFoundException.class).when(notesService).updateNotes(nonExistingId, notesDto);

		// WHEN
		ResponseEntity<String> response = notesController.updateNotes(nonExistingId, notesDto);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Notes with id: " + nonExistingId + ", not found in data base !", response.getBody());
	}

	@Test
	public void deleteNotes_ExistingId_ShouldReturnOkResponse() {
		// GIVEN
		int notesId = 1;

		// WHEN
		ResponseEntity<String> response = notesController.deleteNotes(notesId);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Notes has been deleted from data base.", response.getBody());
		verify(notesService).deleteNotes(notesId);
	}

	@Test
	public void deleteNotes_NonExistingId_ShouldReturnNotFoundResponse() {
		// GIVEN
		int nonExistingId = 999;
		doThrow(NotesNotFoundException.class).when(notesService).deleteNotes(nonExistingId);

		// WHEN
		ResponseEntity<String> response = notesController.deleteNotes(nonExistingId);

		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Notes with id: " + nonExistingId + ", not found in data base !", response.getBody());
	}

}
