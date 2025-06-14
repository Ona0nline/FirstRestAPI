package com.in28minutes.springboot.firstrestapi.surveys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @RequestMapping(value = "/surveys/{surveyid}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyid, @RequestBody Question question){
        String questionid = surveyService.addNewSurveyQuestion(surveyid,question);
//        The URI path of the current request + the id of the question in question
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionid}").buildAndExpand(questionid).toUri();
        return ResponseEntity.created(location ).build();
    }

//    Anytime you have the same URI path on multiple methods, need to specify what method they are using
    @RequestMapping(value = "/surveys/{surveyid}/questions/{questionid}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyid, @PathVariable String questionid){

        surveyService.deleteQuestionById(surveyid,questionid);

        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/surveys/{surveyid}/questions/{questionid}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updatedSurveyQuestion(@PathVariable String surveyid,
                                                        @PathVariable String questionid, @RequestBody Question question){


        surveyService.updateQuestionById(surveyid,questionid, question);

        return ResponseEntity.noContent().build();

    }






}
