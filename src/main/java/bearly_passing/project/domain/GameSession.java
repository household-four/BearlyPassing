package bearly_passing.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id") // The original game
    private Game game;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private int score;

    private boolean completed;

    public void startGame(Student student) {
        // TODO: add game logic
        System.out.println(student.getName() + " is starting " + game.type + " game.");
    }

}
