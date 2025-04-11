package bearly_passing.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.UserRole;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private StudySetRepository studySetRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameSessionRepository gameSessionRepository;

    @InjectMocks
    private AdminService adminService;

    private Student student;
    private Game game;
    private StudySet studySet;
    private GameSession gameSession;
    private Teacher teacher;

    @BeforeEach
    public void init() { 
        student = new Student();
        student.setId(11111111L);
        student.setUsername("Baylor");
        student.setRole(UserRole.STUDENT);
        student.setGrade(89);

        teacher = new Teacher();
        teacher.setName("Mr. Smith");
        teacher.setStudents(Arrays.asList(student));

        studySet = new StudySet();
        studySet.setCreator(student);
        studySet.setTitle("Bearly Passing");
        studySet.setDescription("How to pass every class.");

        game = new Game();
        game.setCreator(student);
        game.setGameType(GameType.MATCHING);
        game.setStudySet(studySet);

        gameSession = new GameSession();
        gameSession.setStudent(student);
        gameSession.setGame(game);
        gameSession.setCompleted(false);
        gameSession.setScore(0);
        gameSession.setCurrentQuestionIndex(0);
    }

    @Test
    public void AdminService_CreateDummyGameSession_ReturnsGameSession() {
        GameSession savedSession = adminService.createDummyGameSession(student, new Teacher(), game);

        assertEquals(gameSession, savedSession);
    }

    @Test
    public void AdminService_StudentGrade_ReturnNameAndGrade() {
        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));

        String result = adminService.studentGrade(student.getId());

        assertEquals("Baylor: 89.0", result);
    }

    @Test
    public void AdminService_TeacherClass_ReturnsStudentsOfTeacher() {
        when(userRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        String result = adminService.teacherClass(teacher.getId());

        String expected = "Teacher: Mr. Smith\n\nStudents:\nBaylor\n";

        assertEquals(expected, result);
    }
}
