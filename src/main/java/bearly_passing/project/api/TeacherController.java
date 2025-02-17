package bearly_passing.project.api;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.services.GameService;
import bearly_passing.project.services.UserService;

@RestController
@RequestMapping("/teacher")

public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public Teacher createTeacher(@RequestParam String name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);

        return userService.createUser(teacher);
    }

    @PostMapping("/{teacherId}/add-student/{studentId}")
    public ResponseEntity<String> addStudentToTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long studentId) {
        userService.addStudentToTeacher(teacherId, studentId);
        return ResponseEntity.ok("Student added successfully!");
    }

    @PostMapping("/create-game/{studySetId}/{creatorId}/{type}")
    public Game createTeacher(
            @PathVariable Long studySetId,
            @PathVariable Long creatorId,
            @PathVariable String type) {
        return gameService.createNewGame(studySetId, creatorId, type);
    }

    @PostMapping("/{teacherId}/assign-game/{studentId}/{gameId}")
    public ResponseEntity<GameSession> assignGameToStudent(
            @PathVariable Long teacherId,
            @PathVariable Long studentId,
            @PathVariable Long gameId) {
        GameSession gameSession = userService.assignGameToStudent(teacherId, studentId, gameId);
        return ResponseEntity.ok(gameSession);
    }

    // POST /teacher/{id}/share-study-set/{study-set-id}
    public void shareStudySet(Long studySetId) {
    } // maybe override with students[] or courseId

    // POST /teacher/{id}/import-canvas-questions
    public void importCanvasQuestions(Long teacherId, Path filePath) {
    }

    // POST /teacher/{id}/give-feedback/{student-id}/{quiz-id}
    public void giveFeedback(Long studentId, Long quizId, String feedback) {
    }

}
