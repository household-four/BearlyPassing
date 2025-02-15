package bearly_passing.project.api;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudySetController {

    // POST /study-set/{id}/create
    public void createQuestionSet(Long ownderId, String name) {}

    // POST /study-set/{id}/add-question
    public void addQuestion(Long questionSetId, String question, String answer) {}

    // GET /study-set/{id}/questions
    public void getQuestions(Long questionSetId) {}

    // POST /study-set/{id}/edit-question/{question-id}
    public void editQuestion(Long questionId, String question, String answer) {}

    // POST /study-set/{id}/delete-question/{question-id}
    public void deleteQuestion(Long questionId) {}
}
