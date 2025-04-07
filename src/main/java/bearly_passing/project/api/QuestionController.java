package bearly_passing.project.api;

import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.User;
import bearly_passing.project.dto.QuestionDTO;
import bearly_passing.project.dto.QuestionMapper;
import bearly_passing.project.dto.UserDTO;
import bearly_passing.project.dto.UserMapper;
import bearly_passing.project.services.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions().stream()
                .map(QuestionMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id) {
        return QuestionMapper.toDTO(questionService.getQuestionById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody Question question) {
        Question saved = questionService.createQuestionWithStudySetValidation(question);
        return ResponseEntity.ok(QuestionMapper.toDTO(saved));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody Question questionUpdate) {
        return ResponseEntity.ok(QuestionMapper.toDTO(questionService.updateQuestion(id, questionUpdate)));
    }
}
