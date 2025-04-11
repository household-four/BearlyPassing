package bearly_passing.project.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.dto.QuestionDTO;
import bearly_passing.project.services.QuestionService;

@WebMvcTest(controllers = GameSessionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private StudySet studySet;
    private Question question;
    private Question question2;
    private List<Question> questions;
    private QuestionDTO questionDTO;

    @BeforeEach
    public void init() { 
        student = new Student();
            student.setId(11111111L);
            student.setUsername("Baylor");
            student.setRole(UserRole.STUDENT);

        studySet = new StudySet();
            studySet.setCreator(student);
            studySet.setTitle("Bearly Passing");
            studySet.setDescription("How to pass every class.");
       
        question = new Question();
        question.setBody("What is the mascot of Baylor?");
        question.setAnswer("Bear");
        question.setGivenAnswer("Bear");
        question.setDifficulty(Question.Difficulty.EASY);
        question.setStudySet(studySet);
        question.setOptions(Arrays.asList("Tiger", "Eagle", "Bear", "Lion"));

        question2 = new Question();
        question2.setBody("What is the mascot of Baylor?");
        question2.setAnswer("Bear");
        question2.setGivenAnswer("Bear");
        question2.setDifficulty(Question.Difficulty.EASY);
        question2.setStudySet(studySet);
        question2.setOptions(Arrays.asList("Tiger", "Eagle", "Bear", "Lion", "Sooners"));

        questions = new ArrayList<>();
        questions.add(question);

        questionDTO = new QuestionDTO();
    }

    @Test
    public void GetAllQuestions() throws Exception {
        // fix this
        when(questionService.getAllQuestions()).thenReturn(null);

        // something about number of questions...
        mockMvc.perform(get("api/question/all/"))
            .andExpect(status().isOk());
    }

    @Test
    public void QuestionController_GetQuestionById_ReturnsQuestionDTO() throws Exception {
        when(questionService.getQuestionById(question.getId())).thenReturn(question);

        mockMvc.perform(get("/api/question/" + question.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(questionDTO)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(questionDTO.getId())));
    }

    @Test
    public void QuestionController_CreateQuestion_ReturnCreated() throws Exception {
        given(questionService.createQuestionWithStudySetValidation(ArgumentMatchers.any()))
            .willAnswer((invocation -> invocation.getArgument(0)));

        mockMvc.perform(post("/api/question/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(questionDTO)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(questionDTO.getId())));
    }

    @Test
    public void QuestionController_UpdateQuestion_ReturnUpdated() throws Exception {
        when(questionService.updateQuestion(question.getId(), question2)).thenReturn(question2);

        mockMvc.perform(put("/api/question/update/" + question.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(questionDTO)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(questionDTO.getId())));
    }
}
