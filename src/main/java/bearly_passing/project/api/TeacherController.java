package bearly_passing.project.api;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bearly_passing.project.domain.Teacher;
import bearly_passing.project.services.UserService;

@RestController
@RequestMapping("/teacher")

public class TeacherController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Teacher createTeacher(@RequestParam String name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);

        return userService.createUser(teacher);
    }

    // POST /teacher/{id}/assign-study-set/{study-set-id}
    public void assignStudySet(Long studentId, Long studySetId) {
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
