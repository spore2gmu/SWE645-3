package com.example.hw.demo.service;



import com.example.hw.demo.model.Survey;
import com.example.hw.demo.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    public Survey updateSurvey(Long id, Survey updatedSurvey) {
        Survey survey = getSurveyById(id);
        if (survey != null) {
            // Update fields
            return saveSurvey(survey);
        }
        return null;
    }
}

