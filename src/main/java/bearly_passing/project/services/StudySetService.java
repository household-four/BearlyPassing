package bearly_passing.project.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Question;
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
    public void saveStudySet(Long studySetId) {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        try {
            new ObjectMapper().writeValue(new File("data/studySets/" + studySet.getName() + ".json"), studySet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public StudySet loadStudySet(Long studySetId) throws IOException {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        Path path = Paths.get("data/studySets/" + studySet.getName() + ".json");

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found: " + path);
        }
        
        try (InputStream input = Files.newInputStream(path)) {
            return new ObjectMapper().readValue(input, StudySet.class);
        }
    }

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
