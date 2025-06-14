package com.in28minutes.springboot.firstrestapi.survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class JsonAssertTest {

    @Test
    void jsonAssert_learningBasics() throws JSONException {
        String expectedresponse = """
                {"id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"}
                """;

        String actualresponse = """
                {     "id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
                """;
//Test will pass even if there are spaces and newline characters
//        Tells you in detail what the difference between your assertions is
//        Setting the strict to false  tells JSON to only compare what is in the expected response string, anything extra will be ignored
        JSONAssert.assertEquals(expectedresponse,actualresponse, true);
    }
}
