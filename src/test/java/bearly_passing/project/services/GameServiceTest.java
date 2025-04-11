package bearly_passing.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.StudentRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameSessionRepository gameSessionRepository;

    @Mock
    private StudySetRepository studySetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameService gameService;

    private Student student;
    private Question question;
    private StudySet studySet;
    private Game game;
    private Game game2;
    private GameSession session;
    private GameSession session2;
    private List<GameSession> aGameSessions;
    private List<Game> games;

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

        game2 = new Game();
        game2.setCreator(student);
        game2.setGameType(GameType.ELIMINATION);
        game2.setStudySet(studySet);

        session = new GameSession();
        session.setId(1L);
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

    // view question

    // answer question

    @Test
    public void GameService_createNewGame_ReturnsGame() {
        when(studySetRepository.findById(studySet.getId())).thenReturn(Optional.of(studySet));
        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        Game savedGame = gameService.createNewGame(studySet.getId(), student.getId(), GameType.MATCHING);

        assertEquals(game, savedGame);
    }

    @Test
    public void GameService_getMyGameSessions_ReturnGameSessions() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        List<GameSession> assignedGames = gameService.getMyGameSessions(student.getId());

        assertEquals(aGameSessions, assignedGames);
    }

    @Test
    public void GameService_GetAllGames_ReturnGames() {
        when(gameRepository.findAll()).thenReturn(games);

        List<Game> savedGames = gameService.getAllGames();

        assertEquals(games, savedGames);
    }
    
    @Test
    public void GameService_GetGameById_ReturnGame() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        Game savedGame = gameService.getGameById(game.getId());

        assertEquals(game, savedGame);
    }

    @Test
    public void GameService_GetGameSessionById_ReturnGameSession() {
        when(gameSessionRepository.findById(session.getId())).thenReturn(Optional.of(session));

        GameSession savedSession = gameService.getGameSessionById(session.getId());

        assertEquals(game, savedSession);
    }

    @Test
    public void GameService_SaveGame_ReturnGame() {
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        Game savedGame = gameService.saveGame(game);

        assertEquals(game, savedGame);
    }

    @Test
    public void GameService_CreateSession_ReturnGameSession() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(gameSessionRepository.save(Mockito.any(GameSession.class))).thenReturn(session2);

        GameSession savedSession = gameService.createSession(game.getId(),student.getId());

        assertEquals(session2, savedSession);
    }

    @Test
    public void GameService_DeleteSession_ReturnsVoid() {
        when(gameSessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
       
        doNothing().when(gameSessionRepository).delete(session);

        assertAll(() -> gameService.deleteSession(session.getId()));
    }

        // get current question DTO
    /*
    @Test
    public void GameService_GetCurrentQuestionDTO_ReturnsGameQuestionDTO() {
        GameSession session3 = new GameSession();
        session3.setGame(game);
        session3.setStudent(student);
        session3.setScore(0);
        session3.setCompleted(false);
        session3.setCurrentQuestionIndex(1);

        when(gameSessionRepository.findById(session.getId())).thenReturn(Optional.of(session3));


    }
    */

    // submit answer
}
