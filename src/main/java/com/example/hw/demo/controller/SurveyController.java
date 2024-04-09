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
        Optional<Survey> surveyOptional = surveyRepository.findById(id);
        if (surveyOptional.isPresent()) {
            surveyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
        Survey updatedSurvey = surveyService.updateSurvey(id, survey);
        return new ResponseEntity<>(updatedSurvey, HttpStatus.OK);
    }
}
