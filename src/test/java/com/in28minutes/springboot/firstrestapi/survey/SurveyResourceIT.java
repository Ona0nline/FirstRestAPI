package com.in28minutes.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Don't want to use a hardcoded port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {
    @Autowired
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/question1";
    private static String ALL_QUESTIONS_URL = "/surveys/Survey1/questions";
//To pass integration tests with Spring security need to add authorization header

    @Test
    void retrieveSpecificQuestion() throws JSONException {
//        Fire a request and want to check if response matches
        HttpHeaders headers = getHttpHeaders();
//       Combining both headers and response body
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange
                (SPECIFIC_QUESTION_URL, HttpMethod.GET,entity, String.class);
        String expectedresponse = """
                {"id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"}
                """;
//        Good practice to check response status and headers first
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedresponse,responseEntity.getBody(),false);

    }

    @Test
    void retrieveSurveyQuestions_basicScenario() throws JSONException{
        HttpHeaders headers = getHttpHeaders();
//       Combining both headers and response body
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange
                (ALL_QUESTIONS_URL, HttpMethod.GET,entity, String.class);
//        Typically want to check that the ids are there, make sure Junit asserts are concise and check for the most relevant things only
        String expectedresponse = """
                [
                  {
                    "id": "Question1"
                   
                  },
                  {
                    "id": "Question2"
                  },
                  {
                    "id": "Question3"
                  }
                ]
                """;

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedresponse,responseEntity.getBody(),false);
    }

//    POST

//    Content type = applocation/json
//    201 created response
//    location: http://localhost:8080/surveys/Survey1/questions/3003246260

    @Test
    void addNewSurveyQuestion_basicscenario() throws JSONException {

//        exchange method allows for more flexibility than postforentity
        String requestbody = """
             {
             "description": "Fav lang?",
                "options": [
                  "Java",
                  "Ruby",
                  "Python",
                  "Javascript"
                ],
                "correctAnswer": "Java"
                }
            """;

        HttpHeaders headers = getHttpHeaders();
//       Combining both headers and response body
        HttpEntity<String> entity = new HttpEntity<String>(requestbody, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange
                (ALL_QUESTIONS_URL, HttpMethod.POST,entity, String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

//        Negating side effect of adding a question to the real list by deleting immediaetly

        String locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(responseEntity.getHeaders().get("Location").get(0).contains("/surveys/Survey1/questions"));
        ResponseEntity<String> responseEntityDelete = testRestTemplate.exchange
                (locationHeader, HttpMethod.DELETE,entity, String.class);
        assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());

//        testRestTemplate.delete(locationHeader);

    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization","Basic " + performBasicAuthEncoding("admin","password"));
        return headers;
    }

    String performBasicAuthEncoding(String user, String password){

        String combined = user + ":" + password;
//        Base64 Encoding => Bytes
        byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
        return new String(encodedBytes);

    }




}
