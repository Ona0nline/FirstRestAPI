package com.in28minutes.springboot.firstrestapi.surveys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SurveyResource {
//    /surveys => surveys
    private SurveyService surveyService;

    @Autowired
    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys(){
        return  surveyService.retrieveAllSurveys();

    }

    @RequestMapping("/surveys/{surveyid}")
    public Survey retrieveSurveyById(@PathVariable String surveyid){
        Survey survey = surveyService.retrieveSpecificSurvey(surveyid);

        if (survey == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return survey;

    }

    @RequestMapping("/surveys/{surveyid}/questions")
    public List<Question> retrieveSurveyQuestions(@PathVariable String surveyid){

        List<Question> questions = surveyService.retrieveAllQuestions(surveyid);

        if (questions == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return questions;

    }

    @RequestMapping("/surveys/{surveyid}/questions/{questionid}")
    public Question retrieveSpecificQuestion(@PathVariable String surveyid, @PathVariable String questionid){

        Question question = surveyService.retrieveQuestionById(surveyid,questionid);
        return question;

    }


}
