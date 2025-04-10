package bearly_passing.project.dto;

import java.util.List;

import bearly_passing.project.domain.GameType;

public class GameDTO {
    private Long id;
    private GameType gameType;
    private Long studySetId;
    private String studySetName;
    private Long userId;
    private List<GameSessionDTO> gameSessions;

    public GameDTO() {
    }

    public GameDTO(Long id, GameType gameType, String studySetName, Long studySetId, Long userId,
            List<GameSessionDTO> gameSessions) {
        this.id = id;
        this.gameType = gameType;
        this.studySetName = studySetName;
        this.studySetId = studySetId;
        this.userId = userId;
        this.gameSessions = gameSessions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getStudySetName() {
        return studySetName;
    }

    public void setStudySetName(String studySetName) {
        this.studySetName = studySetName;
    }

    public Long getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(Long studySetId) {
        this.studySetId = studySetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<GameSessionDTO> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSessionDTO> gameSessions) {
        this.gameSessions = gameSessions;
    }
}
