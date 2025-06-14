package com.in28minutes.springboot.firstrestapi.survey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

//Don't want to use a hardcoded port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {
    @Autowired
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/question1";
    String str = """
                {
                  "id": "Question1",
                  "description": "Most Popular Cloud Platform Today",
                  "options": [
                    "AWS",
                    "Azure",
                    "Google Cloud",
                    "Oracle Cloud"
                  ],
                  "correctAnswer": "AWS"
                }
                """;
    @Test
    void retrieveSpecificQuestion()
    {
//        Fire a request and want to check if response matches
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(SPECIFIC_QUESTION_URL, String.class);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());

    }
}
