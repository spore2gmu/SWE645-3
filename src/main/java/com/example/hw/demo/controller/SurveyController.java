package com.example.hw.demo.controller;

import com.example.hw.demo.model.Survey;
import com.example.hw.demo.repository.SurveyRepository;
import com.example.hw.demo.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SurveyRepository surveyRepository;

    @GetMapping
public ResponseEntity<List<Survey>> getAllSurveys() {
    List<Survey> surveys = surveyRepository.findAll(); // Assuming you are using Spring Data JPA and have a repository named surveyRepository
    return ResponseEntity.ok().body(surveys);
}

    @GetMapping("/{id}")
    public Survey getSurveyById(@PathVariable Long id) {
        return surveyService.getSurveyById(id);
    }


    @PostMapping("/submit")
    public ResponseEntity<?> submitSurvey(@ModelAttribute Survey survey) {
        Survey savedSurvey = surveyService.saveSurvey(survey);
        return new ResponseEntity<>(savedSurvey, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteSurvey(@PathVariable Long id) {
    Optional<Survey> surveyOptional = this.surveyRepository.findById(id);
    if (surveyOptional.isPresent()) {
        this.surveyRepository.deleteById(id);
        // Use ResponseEntity.ok() with body included directly
        return ResponseEntity.ok().body("Deleted successfully");
    } else {
        // Correctly return a response entity with a not found status and message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found");
    }
}


@PutMapping("/{id}")
public ResponseEntity<?> updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
    Optional<Survey> existingSurveyOptional = surveyRepository.findById(id);
    if (existingSurveyOptional.isPresent()) {
        Survey existingSurvey = existingSurveyOptional.get();
        existingSurvey.setFirstName(survey.getFirstName());
        existingSurvey.setLastName(survey.getLastName());
        // Update other fields as needed

        Survey updatedSurvey = surveyService.saveSurvey(existingSurvey); // Assuming saveSurvey also updates
        return ResponseEntity.ok().body(updatedSurvey);
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
