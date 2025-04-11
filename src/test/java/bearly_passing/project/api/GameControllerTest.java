package bearly_passing.project.api;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.dto.GameDTO;
import bearly_passing.project.dto.GameSessionDTO;
import bearly_passing.project.services.GameService;

@WebMvcTest(controllers = GameController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    private Student student;
    private Question question;
    private StudySet studySet;
    private Game game;
    private GameDTO gameDTO;
    private GameSession session;
    private GameSessionDTO gameSessionDTO;
    private GameSession session2;
    private List<GameSession> aGameSessions;
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

        aGameSessions = new ArrayList<GameSession>();
        aGameSessions.add(session);

        student.setAssignedGames(aGameSessions);
    }

    @Test
    public void GameController_GetAllGames_ReturnsGameList() throws Exception {
        when(gameService.getAllGames()).thenReturn(games);
        
        mockMvc.perform(get("/api/game/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(games.size()));
    }

    @Test
    public void GameController_GetGameById_ReturnsGameDTO() throws Exception {
        when(gameService.getGameById(game.getId())).thenReturn(game);

        mockMvc.perform(get("/api/game/" + game.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(gameDTO)))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(gameDTO.getId())))
            .andExpect(jsonPath("$.type", CoreMatchers.is(gameDTO.getGameType())));
    }

    @Test
    public void GameController_CreateGame_ReturnCreated() throws Exception {
        given(gameService.createNewGame(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willAnswer((invocation -> invocation.getArgument(0)));

        mockMvc.perform(post("/api/game/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(gameDTO)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(gameDTO.getId())))
            .andExpect(jsonPath("$.type", CoreMatchers.is(gameDTO.getGameType())));
    }

    @Test
    public void GameController_TestPost_ReturnOK() throws Exception {
        mockMvc.perform(post("/api/game/test"))
            .andExpect(status().isOk())
            .andExpect(content().string("Test OK"));
    }

    @Test
    public void GameController_CreateGameSession_ReturnCreated() throws Exception {
        given(gameService.createSession(ArgumentMatchers.any(), ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        
        mockMvc.perform(post("/api/game/" + game.getId() + "/student/" + student.getId() + "/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(gameSessionDTO)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", CoreMatchers.is(gameSessionDTO.getId())))
            .andExpect(jsonPath("$.type", CoreMatchers.is(gameSessionDTO.getGameType())));
    }
}
