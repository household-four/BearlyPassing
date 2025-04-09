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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        log.info("Getting all questions");
        return questionService.getAllQuestions().stream()
                .map(QuestionMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id) {
        log.info("Getting question {}", id);
        return QuestionMapper.toDTO(questionService.getQuestionById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody Question question) {
        log.info("Creating question");
        Question saved = questionService.createQuestionWithStudySetValidation(question);
        return ResponseEntity.ok(QuestionMapper.toDTO(saved));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody Question questionUpdate) {
        log.info("Updating question {}", id);
        return ResponseEntity.ok(QuestionMapper.toDTO(questionService.updateQuestion(id, questionUpdate)));
    }
}
