package com.mediscreen.NotesPatientService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.NotesPatientService.service.NotesService;
import com.mediscreen.NotesPatientService.dto.NotesDto;
import com.mediscreen.NotesPatientService.exception.NotesNotFoundException;

@RestController
public class NotesController {

	private Logger logger = LoggerFactory.getLogger(NotesController.class);

	@Autowired
	private NotesService notesService;

	@GetMapping("/patHistory/{notesId}")
	public ResponseEntity<NotesDto> getNotesById(@PathVariable("notesId") int notesId) {
		logger.info("GET request - getNotesById " + notesId);

		return ResponseEntity.status(HttpStatus.OK).body(notesService.getNotesById(notesId));
	}

	@GetMapping("/patHistory")
	public ResponseEntity<List<NotesDto>> getAllPatientNotes(@RequestParam int patId) {
		logger.info("GET request - getAllPatientNotes " + patId);

		return ResponseEntity.status(HttpStatus.OK).body(notesService.findByPatientId(patId));
	}

	@PostMapping(path = "/patHistory/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addNotesJson(@RequestBody NotesDto notesDto) {
		logger.info("POST request - addNotes using URL encoded");

		return addNotesInternal(notesDto);
	}

	@PostMapping(path = "/patHistory/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> addNotesUrl(@ModelAttribute NotesDto notesDto) {
	//public ResponseEntity<String> addNotesUrl(@RequestParam int patId, @RequestParam String e) {
		logger.info("POST request - addNotes using URL encoded");
		
		//return addNotesInternal(new NotesDto(patId, e));
		return addNotesInternal(notesDto);
	}

	private ResponseEntity<String> addNotesInternal(NotesDto notesDto) {
		notesService.createNotes(notesDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Notes has been added in the data base.");
	}

	@PutMapping("/patHistory/update/{notesId}")
	public ResponseEntity<String> updateNotes(@PathVariable("notesId") int notesId, @RequestBody NotesDto notesDto) {
		logger.info("PUT request - updateNotes " + notesId);
		
		try {
			notesService.updateNotes(notesId, notesDto);
		} catch (NotesNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Notes with id: " + notesId + ", not found in data base !");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Notes has been updated in data base.");
	}
	
	@DeleteMapping("/patHistory/delete/{notesId}")
	public ResponseEntity<String> deleteNotes(@PathVariable("notesId") int notesId) {
		logger.info("DELETE request - deleteNotes " + notesId);
		
		try {
			notesService.deleteNotes(notesId);
		} catch (NotesNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes with id: " + notesId + ", not found in data base !");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Notes has been deleted from data base.");
	}

}
