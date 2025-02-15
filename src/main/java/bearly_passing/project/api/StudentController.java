package bearly_passing.project.api;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    // GET /student/{id}/study-sets
    public void getAvailableStudySets() { }

    // GET /student/{id}/game-modes/{study-set-id}
    public void getGameModes(Long studySetId) {}

    // POST /student/{id}/play-game/{game-id}
    public void storeGameResult(Long studentId, Long gameId) { }

    // GET /student/{id}/quiz-feedback/{quiz-id}
    public void getQuizFeedback(Long studentId, Long quizId) { }
}
