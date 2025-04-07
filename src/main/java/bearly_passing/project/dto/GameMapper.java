package bearly_passing.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import bearly_passing.project.domain.Game;

public class GameMapper {

    public static GameDTO toDTO(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setGameType(game.getGameType());
        dto.setGameSessions(game.getSessions().stream()
                .map(GameSessionMapper::toDTO)
                .toList());

        if (game.getStudySet() != null) {
            dto.setStudySetName(game.getStudySet().getTitle());
            dto.setStudySetId(game.getStudySet().getId());
        }

        if (game.getCreator() != null) {
            dto.setUserId(game.getCreator().getId());
        }

        return dto;
    }

    public static List<GameDTO> toDTOList(List<Game> games) {
        return games.stream()
                .map(GameMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Optional: You could add a fromDTO() if needed later.
}
