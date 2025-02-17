package bearly_passing.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.User;
import jakarta.transaction.Transactional;

@Service
public class StudySetService {

    @Autowired
    private StudySetRepository studySetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public StudySet createNewStudySet(String name, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudySet studySet = new StudySet();
        studySet.setName(name);
        studySet.setCreator(user);

        return studySetRepository.save(studySet);
    }

    @Transactional
    public StudySet addQuestionToStudySet(Long studySetId, Question question) {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        question.setStudySet(studySet);
        question = questionRepository.save(question);

        studySet.getQuestions().add(question);

        return studySetRepository.save(studySet);
    }
}
