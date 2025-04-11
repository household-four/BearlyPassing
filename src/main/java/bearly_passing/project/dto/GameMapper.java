package bearly_passing.project.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import bearly_passing.project.domain.Game;

public class GameMapper {

    public static GameDTO toDTO(Game game) {
        if (game == null) return null;

        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setGameType(game.getGameType());

        if (game.getSessions() != null) {
            dto.setGameSessions(game.getSessions().stream()
                .map(GameSessionMapper::toDTO)
                .collect(Collectors.toList()));
        } else {
            dto.setGameSessions(Collections.emptyList());
        }


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
        if (games == null) return Collections.emptyList();
        
        return games.stream()
                .map(GameMapper::toDTO)
                .collect(Collectors.toList());
    }

}
