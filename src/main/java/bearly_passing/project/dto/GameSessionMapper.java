package bearly_passing.project.dto;

import bearly_passing.project.domain.GameSession;

public class GameSessionMapper {
    public static GameSessionDTO toDTO(GameSession session) {
        return new GameSessionDTO(
            session.getId(),
            session.getGame() != null ? session.getGame().getId() : null,
            session.getStudent() != null ? session.getStudent().getId() : null,
            session.getScore(),
            session.isCompleted()
        );
    }

    // Optional: if you want to convert from DTO to entity later
    public static GameSession fromDTO(GameSessionDTO dto) {
        GameSession session = new GameSession();
        session.setScore(dto.getScore());
        session.setCompleted(dto.isCompleted());
        return session;
    }
}
