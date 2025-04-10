package bearly_passing.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.StudySet;

public class GameSessionMapper {
    public static GameSessionDTO toDTO(GameSession session) {
        return new GameSessionDTO(
                session.getId(),
                session.getGame() != null ? session.getGame().getId() : null,
                session.getGame() != null ? session.getGame().getGameType().toString() : null,
                session.getStudent() != null ? session.getStudent().getId() : null,
                session.getStudent() != null ? session.getStudent().getName() : null,
                session.getGame() != null ? session.getGame().getStudySet().getId() : null,
                session.getScore(),
                session.isCompleted());
    }

    public static List<GameSessionDTO> toDTOList(List<GameSession> sets) {
        return sets.stream()
                .map(GameSessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Optional: if you want to convert from DTO to entity later
    public static GameSession fromDTO(GameSessionDTO dto) {
        GameSession session = new GameSession();
        session.setScore(dto.getScore());
        session.setCompleted(dto.isCompleted());
        return session;
    }
}
