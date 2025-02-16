package bearly_passing.project.api;

import org.springframework.web.bind.annotation.SessionAttributes;

import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/question")
@SessionAttributes("studySet")
public class QuestionController {

    // create study set icon
    // upload existing || new set || canvas import

    @GetMapping
    public String showQuestionForm() {
        return "question";
    }

    @PostMapping
    public String addQuestion(@ModelAttribute StudySet studySet, Question question) {
        studySet.addQuestion(question);    
        return "redirect:/set/current";
    }

    @ModelAttribute(name = "question")
    public Question question() {
        return new Question();
    }
    
    @ModelAttribute(name = "studySet")
    public StudySet studySet() {
        return new StudySet();
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
