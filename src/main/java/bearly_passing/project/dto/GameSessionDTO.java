package bearly_passing.project.dto;

import bearly_passing.project.domain.GameType;
import bearly_passing.project.domain.StudySet;

public class GameSessionDTO {
    private Long id;
    private Long gameId;
    private String gameType;
    private Long studentId;
    private String studentName;
    private Long studySetId;
    private int score;
    private boolean completed;

    public GameSessionDTO() {
    }

    public GameSessionDTO(
            Long id,
            Long gameId,
            String gameType,
            Long studentId,
            String studentName,
            Long studySetId,
            int score,
            boolean completed) {
        this.id = id;
        this.gameId = gameId;
        this.gameType = gameType;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studySetId = studySetId;
        this.score = score;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(Long id) {
        this.studySetId = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String type) {
        this.gameType = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
