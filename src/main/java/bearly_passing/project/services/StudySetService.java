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
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.User;
import bearly_passing.project.dto.QuestionDTO;
import bearly_passing.project.dto.StudySetDTO;
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
    public StudySet getSetById(Long studySetId) {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        return studySet;
    }

    @Transactional
    public void saveStudySet(Long studySetId) {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        try {
            new ObjectMapper().writeValue(new File("data/studySets/" + studySet.getTitle() + ".json"), studySet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public StudySet loadStudySet(Long studySetId) throws IOException {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        Path path = Paths.get("data/studySets/" + studySet.getTitle() + ".json");

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
    public StudySet loadJsonSet(StudySetDTO studySet) throws IOException {
        User user = userRepository.findById(studySet.getCreator().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudySet newStudySet = new StudySet();
        newStudySet.setTitle(studySet.getTitle());
        newStudySet.setDescription(studySet.getDescription());
        newStudySet.setCreator(user);

        for (QuestionDTO question : studySet.getQuestions()) {
            Question newQuestion = new Question();
            newQuestion.setStudySet(newStudySet);
            newQuestion.setBody(question.getBody());
            newQuestion.setCorrectAnswer(question.getCorrectAnswer());
            newQuestion.setDifficulty(question.getDifficulty());
            questionRepository.save(newQuestion);
            newStudySet.getQuestions().add(newQuestion);
        }
        return studySetRepository.save(newStudySet);
    }

    @Transactional
    public StudySet loadCanvasSet(InputStream inputStream, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // File xml = new File("data/canvasSets/" + canvasFile + ".xml");
        Document document = Jsoup.parse(inputStream, "UTF-8", "");
        // Document document = Jsoup.parse(xml);

        StudySet studySet = new StudySet();
        studySet.setTitle("New Canvas Set");
        studySet.setDescription("New study set created from a Canvas quiz XML file.");
        studySet.setCreator(user);

        for (Question question : parseForQuestions(document)) {
            studySet.getQuestions().add(question);
            question.setStudySet(studySet);
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
            question.setDifficulty(Question.Difficulty.MEDIUM);
            questions.add(question);
        }

        return questions;
    }

    @Transactional
    public StudySet createNewStudySet(String name, Long userId, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudySet studySet = new StudySet();
        studySet.setTitle(name);
        studySet.setDescription(description);
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

    @Transactional
    public List<StudySet> getAllStudySets() {
        return studySetRepository.findAll();
    }

    @Transactional
    public StudySet createStudySet(StudySetDTO dto) {
        StudySet studySet = new StudySet();
        studySet.setTitle(dto.getTitle());
        studySet.setDescription(dto.getDescription());
        return studySetRepository.save(studySet);
    }

    @Transactional
    public List<Game> getGamesByStudySetId(Long studySetId) {
        StudySet studySet = studySetRepository.findById(studySetId)
                .orElseThrow(() -> new RuntimeException("StudySet not found"));

        return studySet.getGames();
    }

}
