package bearly_passing.project.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.dto.GameSessionDTO;
import bearly_passing.project.dto.GameSessionMapper;
import bearly_passing.project.services.GameService;
import bearly_passing.project.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @GetMapping("/all")
    public List<User> getAllStudents() {
        log.info("Fetching all students");
        return userService.getAllStudents();
    }

    @PostMapping("/create")
    public Student createStudent(@RequestParam String name) {
        log.info("Creating student {}", name);
        Student student = new Student();
        student.setName(name);

        return userService.saveUser(student);
    }

    @GetMapping("/my-games")
    public List<GameSessionDTO> getMyGameSessions(@RequestParam Long studentId) {
        log.info("Getting sessions for student {}", studentId);
        return GameSessionMapper.toDTOList(gameService.getMyGameSessions(studentId));
    }

    @GetMapping("/my-teachers")
    public List<Teacher> getMyTeachers(@RequestParam Long studentId) {
        log.info("Getting teachers for student {}", studentId);
        return userService.getTeachersByStudentId(studentId);
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
