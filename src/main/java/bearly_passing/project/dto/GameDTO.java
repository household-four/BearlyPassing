package bearly_passing.project.dto;

public class GameDTO {
    private Long id;
    private String gameType;
    private Long studySetId;
    private Long userId;

    public GameDTO() {}

    public GameDTO(Long id, String gameType, Long studySetId, Long userId) {
        this.id = id;
        this.gameType = gameType;
        this.studySetId = studySetId;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public Long getStudySetId() { return studySetId; }
    public void setStudySetId(Long studySetId) { this.studySetId = studySetId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
