package bearly_passing.project.api;

import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.services.QuestionService;
import bearly_passing.project.services.StudySetService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private StudySetService studySetService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/add")
    public StudySet addQuestion(
            @RequestParam Long studySetId,
            @RequestBody Question question) {

        return studySetService.addQuestionToStudySet(studySetId, question);
    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

}
