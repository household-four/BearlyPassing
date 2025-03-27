package bearly_passing.project.dto;

import bearly_passing.project.domain.Question.Difficulty;

public class QuestionDTO {
    private Long id;
    private String body;
    private String correctAnswer;
    private Difficulty difficulty;

    public QuestionDTO() {}

    public QuestionDTO(Long id, String body, String correctAnswer, Difficulty difficulty) {
        this.id = id;
        this.body = body;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
}
