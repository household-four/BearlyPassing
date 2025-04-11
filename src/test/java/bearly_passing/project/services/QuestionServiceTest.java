package bearly_passing.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private StudySetRepository studySetRepository;

    @InjectMocks
    private QuestionService questionService;

    private Student student;
    private StudySet studySet;
    private Question question;
    private Question question2;
    private List<Question> questions;

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
    }

    @Test
    public void QuestionService_GetAllQuestions_ReturnsListQuestions() {
        when(questionRepository.findAll()).thenReturn(questions);
        
        List<Question> savedQuestions = questionService.getAllQuestions();

        assertEquals(questions, savedQuestions);
    }

    @Test
    public void QuestionService_SaveQuestion_ReturnsSavedQuestion() {
        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);
        
        Question savedQuestion = questionService.saveQuestion(question);
        
        assertEquals(question, savedQuestion);
    }

    @Test
    public void QuestionService_GetQuestionById_ReturnsQuestion() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        
        Question savedQuestion = questionService.getQuestionById(question.getId());

        assertEquals(question, savedQuestion);
    }

    @Test
    public void QuestionService_CreateQuestionWithStudySetValidation_ReturnsQuestion() {
        when(studySetRepository.findById(studySet.getId())).thenReturn(Optional.of(studySet));
        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);
        
        Question savedQuestion = questionService.createQuestionWithStudySetValidation(question);
        
        assertEquals(question, savedQuestion);
    }

    @Test
    public void QuestionService_UpdateQuestion_ReturnsUpdatedQuestion() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question2);

        Question savedQuestion = questionService.updateQuestion(question.getId(), question2);

        assertEquals(question2, savedQuestion);
    }
}
