package bearly_passing.project.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        
        StudySet new_set;
        try (InputStream input = Files.newInputStream(path)) {
            new_set = new ObjectMapper().readValue(input, StudySet.class);
        }

        for (Question question : new_set.getQuestions()) {
            questionRepository.save(question);
        }

        return studySetRepository.save(new_set);
    }

    @Transactional
    public StudySet loadCanvasSet(String canvasFile) throws IOException {

        File xml = new File("data/canvasSets/" + canvasFile + ".xml");
        Document document = Jsoup.parse(xml);

        StudySet studySet = new StudySet();
        for (Question question : parseForQuestions(document)) {
            studySet.addQuestion(question);
            questionRepository.save(question);
        }
        
        return studySetRepository.save(studySet);
    }

    private ArrayList<Question> parseForQuestions(Document document) {
        
        ArrayList<Question> questions = new ArrayList<>();

        for (Element item : document.select("item")) {

            Question question = new Question();
            
            question.setBody(item.selectFirst("presentation material mattext").text());
            
            String answerId = item.selectFirst("resprocessing respcondition conditionvar varequal").text();
            for (Element response : item.select("response_label")) {
                if (response.attr("ident").equals(answerId)) {
                    question.setAnswer(response.selectFirst("material mattext").text());
                }
            }
        
            questions.add(question);
        }
        
        return questions;
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


    public List<StudySet> getAllStudySets() {
        return studySetRepository.findAll();
    }
}
