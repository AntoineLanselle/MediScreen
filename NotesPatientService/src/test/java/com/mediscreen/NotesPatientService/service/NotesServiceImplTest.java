package com.mediscreen.NotesPatientService.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import com.mediscreen.NotesPatientService.domain.Notes;
import com.mediscreen.NotesPatientService.dto.NotesDto;
import com.mediscreen.NotesPatientService.exception.NotesNotFoundException;
import com.mediscreen.NotesPatientService.repository.NotesRepository;

class NotesServiceImplTest {

	@Mock
	private NotesRepository notesRepository;

	@Mock
	private MongoOperations mongoOperations;

	@InjectMocks
	private NotesServiceImpl notesService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getNotesById_ExistingId_ShouldReturnNotesDto() {
		// GIVEN
		int notesId = 1;
		Notes notes = new Notes();
		notes.setNotesId(notesId);
		when(notesRepository.findByNotesId(notesId)).thenReturn(notes);

		// WHEN
		NotesDto result = notesService.getNotesById(notesId);

		// THEN
		assertNotNull(result);
		assertEquals(notesId, result.getNotesId());
	}

	@Test
	public void getNotesById_NonExistingId_ShouldThrowNotesNotFoundException() {
		// GIVEN
		int nonExistingId = 999;
		when(notesRepository.findByNotesId(nonExistingId)).thenReturn(null);

		// WHEN & THEN
		assertThrows(NotesNotFoundException.class, () -> notesService.getNotesById(nonExistingId));
	}

	@Test
	public void findByPatientId_ShouldReturnListOfNotesDto() {
		// GIVEN
		int patientId = 123;
		List<Notes> notesList = new ArrayList<>();
		notesList.add(new Notes());
		when(notesRepository.findByPatientIdOrderByDateTimeNoteDesc(patientId)).thenReturn(notesList);

		// WHEN
		List<NotesDto> result = notesService.findByPatientId(patientId);

		// THEN
		assertNotNull(result);
		assertEquals(notesList.size(), result.size());
	}

	@Test
	public void updateNotes_ExistingId_ShouldUpdateNotesInRepository() {
		// GIVEN
		int notesId = 1;
		NotesDto updateNotes = new NotesDto();
		updateNotes.setE("Updated notes");
		Notes existingNotes = new Notes();
		existingNotes.setNotes("Original notes");
		when(notesRepository.findByNotesId(notesId)).thenReturn(existingNotes);
		when(notesRepository.save(any(Notes.class))).thenAnswer(invocation -> {
			Notes updatedNotes = invocation.getArgument(0);
			existingNotes.setNotes(updatedNotes.getNotes());
			return existingNotes;
		});

		// WHEN
		notesService.updateNotes(notesId, updateNotes);

		// THEN
		assertEquals(updateNotes.getE(), existingNotes.getNotes());
		verify(notesRepository).findByNotesId(notesId);
		verify(notesRepository).save(existingNotes);
	}

	@Test
	public void updateNotes_NonExistingId_ShouldThrowNotesNotFoundException() {
		// GIVEN
		int nonExistingId = 999;
		NotesDto updateNotes = new NotesDto();
		when(notesRepository.findByNotesId(nonExistingId)).thenReturn(null);

		// WHEN & THEN
		assertThrows(NotesNotFoundException.class, () -> notesService.updateNotes(nonExistingId, updateNotes));
	}

	@Test
	public void deleteNotes_ExistingId_ShouldDeleteNotesFromRepository() {
		// GIVEN
		int notesId = 1;
		Notes existingNotes = new Notes();
		when(notesRepository.findByNotesId(notesId)).thenReturn(existingNotes);
		doNothing().when(notesRepository).delete(existingNotes);

		// WHEN & THEN
		assertDoesNotThrow(() -> notesService.deleteNotes(notesId));
	}

	@Test
	public void deleteNotes_NonExistingId_ShouldThrowNotesNotFoundException() {
		// GIVEN
		int nonExistingId = 999;
		when(notesRepository.findByNotesId(nonExistingId)).thenReturn(null);

		// WHEN & THEN
		assertThrows(NotesNotFoundException.class, () -> notesService.deleteNotes(nonExistingId));
	}

}
