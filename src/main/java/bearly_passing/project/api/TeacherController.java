package bearly_passing.project.api;

import java.nio.file.Path;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {

    // POST /teacher/{id}/assign-study-set/{study-set-id}
    public void assignStudySet(Long studentId, Long studySetId) { }

    // POST /teacher/{id}/share-study-set/{study-set-id}
    public void shareStudySet(Long studySetId) {} // maybe override with students[] or courseId

    // POST /teacher/{id}/import-canvas-questions
    public void importCanvasQuestions(Long teacherId, Path filePath) {}

    // POST /teacher/{id}/give-feedback/{student-id}/{quiz-id}
    public void giveFeedback(Long studentId, Long quizId, String feedback) {}

}
