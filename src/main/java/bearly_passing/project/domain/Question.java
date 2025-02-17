package bearly_passing.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String body;
    private String correctAnswer;
    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "study_set")
    private StudySet studySet;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public long getID() {
        return id;
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
}
