package bearly_passing.project.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.dto.GameAnswerDTO;
import bearly_passing.project.dto.GameDTO;
import bearly_passing.project.dto.GameQuestionDTO;
import bearly_passing.project.dto.GameSessionDTO;
import bearly_passing.project.services.GameService;

@WebMvcTest(controllers = GameSessionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GameSessionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    private Student student;
    private Question question;
    private StudySet studySet;
    private Game game;
    private GameDTO gameDTO;
    private GameQuestionDTO gameQuestionDTO;
    private GameSession session;
    private GameSessionDTO gameSessionDTO;
    private GameSession session2;
    private List<GameSession> sessions;
    private List<Game> games;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        student = new Student();
        student.setId(11111111L);
        student.setUsername("Baylor");
        student.setRole(UserRole.STUDENT);

        question = new Question();

        studySet = new StudySet();
        studySet.setCreator(student);
        studySet.setTitle("Bearly Passing");
        studySet.setDescription("How to pass every class.");

        question.setStudySet(studySet);

        gameQuestionDTO = new GameQuestionDTO();

        game = new Game();
        game.setCreator(student);
        game.setGameType(GameType.MATCHING);
        game.setStudySet(studySet);

        gameDTO = new GameDTO();
        gameSessionDTO = new GameSessionDTO();

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

        student.setAssignedGames(sessions);
    }

    @Test
    public void GameSessionController_GetSessionById_ReturnsGameSessionDTO() throws Exception {
        when(gameService.getGameSessionById(game.getId())).thenReturn(session);

        mockMvc.perform(get("/api/gamesession/" + game.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(gameSessionDTO)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(gameSessionDTO.getId())))
            .andExpect(jsonPath("$.type", CoreMatchers.is(gameSessionDTO.getGameType())));
    }

    @Test
    public void GameSessionController_GetSessionsForStudent_ReturnsGameSessionDTOs() throws Exception {
        when(gameService.getMyGameSessions(student.getId())).thenReturn(sessions);

        mockMvc.perform(get("/api/gamesession/student/" + student.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(student)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(sessions.size()));
    }

    @Test
    public void GameSessionController_GetCurrentQuestion_ReturnsGameQuestionDTO() throws Exception {
        when(gameService.getCurrentQuestionDTO(session.getId())).thenReturn(gameQuestionDTO);

        mockMvc.perform(get("/api/gamesession//question/" + session.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(student)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(gameSessionDTO.getId())));
    }

    @Test
    public void GameSessionController_SubmitAnswer_ReturnsMappedAnswer() throws Exception {
        GameAnswerDTO answerDTO = new GameAnswerDTO();
        answerDTO.setGameSessionId(session.getId());
        answerDTO.setQuestionId(question.getId());
        answerDTO.setSubmittedAnswer("Bear");
        
        Map<String, String> response = new HashMap<>();
        response.put("result", "correct");

        when(gameService.submitAnswer(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(get("/api/gamesession//question/" + session.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(answerDTO)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("correct"));
    }

    @Test
    public void GameSessionController_DeleteGameSession_ReturnsNoContent() throws Exception {
        doNothing().when(gameService).deleteSession(session.getId());

        mockMvc.perform(delete("/api/gamesession/delete/" + session.getId()))
            .andExpect(status().isNoContent());
    }

}
