package bearly_passing.project.dto;

public class GameSessionDTO {
    private Long id;
    private Long gameId;
    private Long studentId;
    private int score;
    private boolean completed;

    public GameSessionDTO() {}

    public GameSessionDTO(Long id, Long gameId, Long studentId, int score, boolean completed) {
        this.id = id;
        this.gameId = gameId;
        this.studentId = studentId;
        this.score = score;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
