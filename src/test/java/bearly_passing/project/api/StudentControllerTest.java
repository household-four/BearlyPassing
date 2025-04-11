package bearly_passing.project.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.services.GameService;
import bearly_passing.project.services.UserService;

@WebMvcTest(controllers = GameSessionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private GameService gameService;

    private Student student;
    private StudySet studySet;
    private Student student2;
    private Student student3;
    private Teacher teacher;
    private List<User> users;
    private Game game;
    private GameSession session;
    private GameSession session2;
    private List<GameSession> sessions;
    private List<Teacher> teachers;

    @BeforeEach
    public void init() {
        student = new Student();
            student.setId(11111111L);
            student.setUsername("Baylor");
            student.setRole(UserRole.STUDENT);

        student2 = new Student();
            student2.setId(22222222L);
            student2.setUsername("Bear");
            student2.setRole(UserRole.STUDENT);

        student3 = new Student();
            student3.setId(33333333L);
            student3.setUsername("Green");
            student3.setRole(UserRole.STUDENT);

        teacher = new Teacher();
            teacher.setId(44444444L);
            teacher.setName("Gold");

        teachers = new ArrayList<>();
        teachers.add(teacher);

        users = new ArrayList<>();
        users.add(student);
        users.add(student2);
        users.add(student3);

        session = new GameSession();
        session.setGame(game);
        session.setStudent(student);

        session2 = new GameSession();
        session2.setGame(game);
        session2.setStudent(student);
        session2.setScore(0);
        session2.setCompleted(false);

        sessions = new ArrayList<GameSession>();
        sessions.add(session);

        studySet = new StudySet();
        studySet.setCreator(student);
        studySet.setTitle("Bearly Passing");
        studySet.setDescription("How to pass every class.");
  
        game = new Game();
        game.setCreator(student);
        game.setGameType(GameType.MATCHING);
        game.setStudySet(studySet);
    }

    @Test
    public void StudentController_GetAllStudents_ReturnsStudentList() throws Exception {
        when(userService.getAllStudents()).thenReturn(users);
        
        mockMvc.perform(get("/api/student/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void QuestionController_CreateQuestion_ReturnCreated() throws Exception {
        when(userService.saveUser(Mockito.any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/student/create")
            .contentType(MediaType.APPLICATION_JSON)
            .param("name", "" + student.getName()))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", CoreMatchers.is(student.getName())));
    }

    @Test
    public void QuestionController_GetMyGameSessions_ReturnsSessionDTOs() throws Exception {
        when(gameService.getMyGameSessions(student.getId())).thenReturn(sessions);

        mockMvc.perform(get("/api/student/my-games")
            .contentType(MediaType.APPLICATION_JSON)
            .param("studentId", "" + student.getId()))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(sessions.size()));
    }
    
    @Test
    public void QuestionController_GetMyTeachers_ReturnsTeachers() throws Exception {
        when(userService.getTeachersByStudentId(student.getId())).thenReturn(teachers);
        
        mockMvc.perform(get("/api/student/my-teachers")
            .param("studentId", "" + student.getId()))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(teachers.size()));
    }
}
