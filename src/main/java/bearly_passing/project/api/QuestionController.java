package bearly_passing.project.api;

import org.springframework.web.bind.annotation.SessionAttributes;

import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.services.StudySetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/question")
// @SessionAttributes("studySet")
public class QuestionController {

    // TODO: Maybe remove? I think Christen added this in conflict with my additions

    // create study set icon
    // upload existing || new set || canvas import

    @Autowired
    private StudySetService studySetService;

    // @GetMapping
    // public String showQuestionForm() {
    // return "question";
    // }

    // @PostMapping
    // public String addQuestion(@ModelAttribute StudySet studySet, Question
    // question) {
    // studySet.addQuestion(question);
    // return "redirect:/set/current";
    // }

    @PostMapping("/add")
    public StudySet addQuestion(
            @RequestParam Long studySetId,
            @RequestBody Question question) {

        return studySetService.addQuestionToStudySet(studySetId, question);
    }

    @ModelAttribute(name = "question")
    public Question question() {
        return new Question();
    }

    @ModelAttribute(name = "studySet")
    public StudySet studySet() {
        return new StudySet();
    }

}
