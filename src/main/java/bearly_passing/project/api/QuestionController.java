package bearly_passing.project.api;

import bearly_passing.project.domain.Question;
import bearly_passing.project.dto.QuestionDTO;
import bearly_passing.project.dto.QuestionMapper;
import bearly_passing.project.services.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return questions.stream()
                .map(QuestionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public QuestionDTO addQuestion(@RequestBody Question question) {
        Question saved = questionService.saveQuestion(question);
        return QuestionMapper.toDTO(saved);
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return QuestionMapper.toDTO(question);
    }

}
