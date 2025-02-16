package bearly_passing.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bearly_passing.project.domain.Student;
import bearly_passing.project.services.UserService;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Student createStudent(@RequestParam String name) {
        Student student = new Student();
        student.setName(name);

        return userService.createUser(student);
    }

    // GET /student/{id}/study-sets
    public void getAvailableStudySets() {
    }

    // GET /student/{id}/game-modes/{study-set-id}
    public void getGameModes(Long studySetId) {
    }

    // POST /student/{id}/play-game/{game-id}
    public void storeGameResult(Long studentId, Long gameId) {
    }

    // GET /student/{id}/quiz-feedback/{quiz-id}
    public void getQuizFeedback(Long studentId, Long quizId) {
    }
}
