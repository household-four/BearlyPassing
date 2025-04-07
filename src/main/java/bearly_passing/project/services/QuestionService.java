package bearly_passing.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudySetRepository studySetRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public Question createQuestionWithStudySetValidation(Question question) {
        StudySet set = studySetRepository.findById(question.getStudySet().getId())
                .orElseThrow(() -> new RuntimeException("StudySet not found"));
        question.setStudySet(set);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question questionUpdate) {
        Question existing = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        existing.setBody(questionUpdate.getBody());
        existing.setDifficulty(questionUpdate.getDifficulty());
        existing.setCorrectAnswer(questionUpdate.getCorrectAnswer());
        return questionRepository.save(existing);
    }
}
