package bearly_passing.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;
    private String correctAnswer;
    private String givenAnswer;
    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "studyset_id")
    private StudySet studySet;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}
