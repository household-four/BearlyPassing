package bearly_passing.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String body;
    private String correctAnswer;
    private String givenAnswer;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "study_set_id")
    private StudySet studySet;

    @ElementCollection
    private List<String> options;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public Long getId() {
        return this.id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnswer() {
        return correctAnswer;
    }

    public void setAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public StudySet getStudySet() {
        return studySet;
    }

    public void setStudySet(StudySet studySet) {
        this.studySet = studySet;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
