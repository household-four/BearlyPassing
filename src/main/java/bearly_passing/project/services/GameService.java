package bearly_passing.project.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GameSessionRepository gameSessionRepository;

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
}
