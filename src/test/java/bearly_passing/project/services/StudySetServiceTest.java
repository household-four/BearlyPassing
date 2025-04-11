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

import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudentRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.UserRole;
import bearly_passing.project.dto.StudySetDTO;

@ExtendWith(MockitoExtension.class)
public class StudySetServiceTest {

    @Mock
    private StudySetRepository studySetRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private StudySetService studySetService;

    private Student student;
    private StudySet studySet;
    private Game game;
    private List<Game> games;
    private StudySetDTO studySetDTO;
    private StudySet studySet2;
    private List<StudySet> studySets;
    private Question question;

    @BeforeEach
    public void init() {
        student = new Student();
            student.setId(11111111L);
            student.setUsername("Baylor");
            student.setRole(UserRole.STUDENT);

        studySet = new StudySet();
            studySet.setId(1L);
            studySet.setCreator(student);
            studySet.setTitle("Bearly Passing");
            studySet.setDescription("How to pass every class.");

        studySetDTO = new StudySetDTO();
            studySet.setCreator(student);
            studySet.setTitle("Bearly Passing");
            studySet.setDescription("How to pass every class.");

        studySet2 = new StudySet();
            studySet2.setCreator(student);
            studySet2.setTitle("Bearly Passing 2");
            studySet2.setDescription("How to pass some classes.");

        studySets = new ArrayList<StudySet>();
            studySets.add(studySet);
            studySets.add(studySet2);

        question = new Question();
            question.setStudySet(studySet);

        studySet.addQuestion(question);

        games = new ArrayList<>();
        game = new Game();
        game.setId(3L);
        game.setCreator(student);
        game.setGameType(GameType.MATCHING);
        game.setStudySet(studySet);

        games.add(game);

        studySet.getGames().add(game);
    }

    @Test
    public void StudySetService_getSetById_ReturnsStudySet() {
        
        when(studySetRepository.findById(studySet.getId())).thenReturn(Optional.of(studySet));
        
        StudySet savedStudySet = studySetService.getSetById(studySet.getId());

        assertEquals(studySet, savedStudySet);
    }

    // save the study set to file
    @Test
    public void StudySetService_SaveStudySet_ReturnsVoid() {

    }

    // load study set from file

    // load canvas set from file

    // parse for questions

    @Test
    public void StudySetService_CreateNewStudySet_ReturnsStudySet() {

        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studySetRepository.save(Mockito.any(StudySet.class))).thenReturn(studySet);

        StudySet savedStudySet = studySetService.createNewStudySet(studySet.getTitle(), student.getId(), studySet.getDescription());

        assertEquals(studySet, savedStudySet);
    }

    @Test
    public void StudySetService_AddQuestionToStudySet_ReturnsStudySet() {
        when(studySetRepository.findById(studySet.getId())).thenReturn(Optional.of(studySet));
        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);
        when(studySetRepository.save(Mockito.any(StudySet.class))).thenReturn(studySet);

        StudySet savedStudySet = studySetService.addQuestionToStudySet(studySet.getId(), question);

        assertEquals(studySet, savedStudySet);
    }

    @Test
    public void StudySetService_GetAllStudySets_ReturnsStudySets() {
        when(studySetRepository.findAll()).thenReturn(studySets);

        List<StudySet> savedStudySets = studySetService.getAllStudySets();

        assertEquals(studySets, savedStudySets);
    }

    @Test
    public void StudySetService_CreateStudySet_ReturnsStudySet() {
        when(studySetRepository.save(Mockito.any(StudySet.class))).thenReturn(studySet);

        StudySet newStudySet = studySetService.createStudySet(studySetDTO);

        assertEquals(studySet, newStudySet);
    }

    // how to add games to a study set?
    @Test
    public void StudySetService_GetGamesByStudySetId_ReturnsGames() {
        when(studySetRepository.findById(studySet.getId())).thenReturn(Optional.of(studySet));

        List<Game> savedGames = studySetService.getGamesByStudySetId(studySet.getId());

        System.out.println();

        assertEquals(games, savedGames);
    }

}
