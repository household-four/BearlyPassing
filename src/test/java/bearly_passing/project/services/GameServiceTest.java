package bearly_passing.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
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
import bearly_passing.project.dto.StudySetDTO;

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
    private StudySet studySet;
    private Game game;
    private GameSession session;
    private List<GameSession> aGameSessions;

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

        game = new Game();
        game.setCreator(student);
        game.setGameType(GameType.MATCHING);
        game.setStudySet(studySet);

        session = new GameSession();
        session.setGame(game);
        session.setStudent(student);

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

    // get all games
    
    // get game by id

    // get game session by id

    // save game

    // get current question DTO

    // submit answer

    // create session

    // delete session
}
