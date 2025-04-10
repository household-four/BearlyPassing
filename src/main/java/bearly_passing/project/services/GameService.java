package bearly_passing.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.StudentRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.dto.GameQuestionDTO;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GameService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GameRepository gameRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private StudySetRepository studySetRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String viewQuestion(long studentID, long studysetID, long gameID, long questionID) {
        return em.createQuery("SELECT q FROM Question q WHERE q.studySet.id = :id", Question.class)
                .setParameter("id", studysetID)
                .getResultList().get(0)
                .getBody();
    }

    @Transactional
    public String answerQuestion(long studentID, long studysetID, long gameID, long questionID, String answer) {
        String correctAnswer = em.createQuery("SELECT q FROM Question q WHERE q.studySet.id = :id", Question.class)
                .setParameter("id", studysetID)
                .getResultList().get(0)
                .getCorrectAnswer();

        if (answer.equals(correctAnswer))
            return "correct";
        return "incorrect - feedback";
    }

    @Transactional
    public Game createNewGame(Long studySetId, Long creatorId, String type) {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("Study set not found"));

        User user = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = new Game();
        game.setCreator(user);
        game.setGameType(type);
        game.setStudySet(studySet);

        return gameRepository.save(game);
    }

    public List<GameSession> getMyGameSessions(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getAssignedGames();

    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public GameQuestionDTO getCurrentQuestionDTO(Long gameSessionId) {
        GameSession session = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found"));

        List<Question> questions = session.getGame().getStudySet().getQuestions();
        int index = session.getCurrentQuestionIndex();

        if (index < 0 || index >= questions.size()) {
            throw new RuntimeException("Invalid question index");
        }

        Question currentQuestion = questions.get(index);

        GameQuestionDTO dto = new GameQuestionDTO();
        dto.setQuestionId(currentQuestion.getId());
        dto.setQuestionBody(currentQuestion.getBody());
        dto.setAnswerOptions(currentQuestion.getOptions()); // assuming this returns a List<String>

        return dto;
    }

    @Transactional
    public String submitAnswer(Long gameSessionId, Long questionId, String submittedAnswer) {
        GameSession session = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found"));

        List<Question> questions = session.getGame().getStudySet().getQuestions();
        int index = session.getCurrentQuestionIndex();

        if (index < 0 || index >= questions.size()) {
            throw new RuntimeException("Invalid question index");
        }

        Question currentQuestion = questions.get(index);
        boolean isCorrect = currentQuestion.getAnswer().equalsIgnoreCase(submittedAnswer);

        if (isCorrect) {
            session.setScore(session.getScore() + 1);
        }

        session.setCurrentQuestionIndex(index + 1);

        // If it was the last question, mark complete
        if (session.getCurrentQuestionIndex() >= questions.size()) {
            session.setCompleted(true);
        }

        gameSessionRepository.save(session);

        return isCorrect ? "Correct!" : "Incorrect. Try again.";
    }

    public GameSession createSession(Long gameId, Long studentId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        GameSession session = new GameSession();
        session.setGame(game);
        session.setStudent(student);
        session.setScore(0);
        session.setCompleted(false);

        return gameSessionRepository.save(session);
    }

    public void deleteSession(Long gameSessionId) {
        GameSession session = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found"));

        gameSessionRepository.delete(session);
    }

}
