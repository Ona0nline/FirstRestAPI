package com.in28minutes.springboot.firstrestapi.survey;

import com.in28minutes.springboot.firstrestapi.surveys.Question;
import com.in28minutes.springboot.firstrestapi.surveys.SurveyResource;
import com.in28minutes.springboot.firstrestapi.surveys.SurveyService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = SurveyResource.class)
//To disable filters, aka that chain of command before a request gets approved
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {
//    @MockBean
    private SurveyService surveyService;
    @Autowired
    private MockMvc mockMvc;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/question1";
//    Mock this method -> surveyService.retrieveQuestionById(surveyid,questionid); since it's a web layer communicating with a business layer
    @Test
    void retrieveSpecificQuestion_200Scenario() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                get(SPECIFIC_QUESTION_URL).
                accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println(mvcResult.getResponse().getStatus());
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void retrieveSpecificQuestion_BasicScenario() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                get(SPECIFIC_QUESTION_URL).
                accept(MediaType.APPLICATION_JSON);
        Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        String expectedresult = """
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

        when(surveyService.retrieveQuestionById("Survey1", "Question1")).thenReturn(question);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expectedresult, mvcResult.getResponse().getContentAsString(), false);
    }
}
