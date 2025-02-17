package bearly_passing.project.api;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.domain.StudySet;

@Controller
@RequestMapping("/set")
@SessionAttributes("studySet")
public class StudySetController {

    private final StudySetRepository studySetRepository;

    public StudySetController(StudySetRepository studySetRepository) {
        this.studySetRepository = studySetRepository;
    }

    // create study set icon
    // upload existing || new set || canvas import

    @GetMapping("/current")
    public String getMethodName() {
        return "set";
    }
    

    @PostMapping
    public String questionSubmit(StudySet studySet) {
        studySetRepository.save(studySet);
        return "redirect:/create";
    }
    
    // // POST /study-set/create
    // public void createQuestionSet(String name) {}

    // // POST /study-set/{id}/add-question
    // public void addQuestion(Long questionSetId, String question, String answer) {}

    // // GET /study-set/{id}/questions
    // public void getQuestions(Long questionSetId) {}

    // // POST /study-set/{id}/edit-question/{question-id}
    // public void editQuestion(Long questionId, String question, String answer) {}

    // // POST /study-set/{id}/delete-question/{question-id}
    // public void deleteQuestion(Long questionId) {}
}
