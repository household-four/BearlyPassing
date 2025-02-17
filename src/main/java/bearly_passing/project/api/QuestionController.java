package bearly_passing.project.api;

import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.services.StudySetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/question")
public class QuestionController {

    // upload existing || new set || canvas import

    @Autowired
    private StudySetService studySetService;

    @PostMapping("/add")
    public StudySet addQuestion(
            @RequestParam Long studySetId,
            @RequestBody Question question) {

        return studySetService.addQuestionToStudySet(studySetId, question);
    }

}
