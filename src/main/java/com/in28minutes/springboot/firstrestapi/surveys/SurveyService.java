package com.in28minutes.springboot.firstrestapi.surveys;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);

    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSpecificSurvey(String id){
        Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(id)).findFirst();
        if(optionalSurvey.isEmpty()) return null;

        return optionalSurvey.get();
    }

    public List<Question> retrieveAllQuestions(String surveyid){
        Survey survey = retrieveSpecificSurvey(surveyid);

        if(survey == null) return null;
        return survey.getQuestions();
    }

    public Question retrieveQuestionById(String surveyid, String questionid) {
        Survey survey = retrieveSpecificSurvey(surveyid);
        List<Question> Questions = survey.getQuestions();

        Optional<Question> optionalQuestion = Questions.stream().filter(questionsearch -> questionid.equalsIgnoreCase(questionsearch.getId())).findFirst();

        if(optionalQuestion.isEmpty()) return null;
        return optionalQuestion.get();
    }
}
