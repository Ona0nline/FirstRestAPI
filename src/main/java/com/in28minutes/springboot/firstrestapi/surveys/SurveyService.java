package com.in28minutes.springboot.firstrestapi.surveys;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static final List<Survey> surveys = new ArrayList<>();

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

    public Survey retrieveSpecificSurvey(String id) {
        Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(id)).findFirst();
        return optionalSurvey.orElse(null);

    }

    public List<Question> retrieveAllQuestions(String surveyid) {
        Survey survey = retrieveSpecificSurvey(surveyid);

        if (survey == null) return null;
        return survey.getQuestions();
    }

    public Question retrieveQuestionById(String surveyid, String questionid) {
        Survey survey = retrieveSpecificSurvey(surveyid);
        List<Question> Questions = survey.getQuestions();

        Optional<Question> optionalQuestion = Questions.stream().filter(questionsearch -> questionid.equalsIgnoreCase(questionsearch.getId())).findFirst();
        return optionalQuestion.orElse(null);
    }

    public String addNewSurveyQuestion(String surveyid, Question question) {
        List<Question> questions = retrieveAllQuestions(surveyid);
        question.setId(generaterandomid());
        questions.add(question);
        return question.getId();

    }

    private static String generaterandomid() {
        SecureRandom securerandom = new SecureRandom();
        return new BigInteger(32, securerandom).toString();
    }

    public String deleteQuestionById(String surveyid, String questionid) {
        List<Question> surveyQuestions = retrieveAllQuestions(surveyid);
        if (surveyQuestions == null) return null;
        Predicate<Question> questionPredicate = questionsearch -> questionid.equalsIgnoreCase(questionsearch.getId());
        boolean removed = surveyQuestions.removeIf(questionPredicate);
        if (!removed) return null;
        return questionid;
    }

    public void updateQuestionById(String surveyid, String questionid, Question question) {
        List<Question> surveyQuestions = retrieveAllQuestions(surveyid);
        surveyQuestions.removeIf(q -> q.getId().equalsIgnoreCase(questionid));
        surveyQuestions.add(question);
    }
}
