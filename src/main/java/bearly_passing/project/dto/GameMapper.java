package bearly_passing.project.dto;

import bearly_passing.project.domain.Game;

public class GameMapper {

    public static GameDTO toDTO(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setGameType(game.getGameType());

        if (game.getStudySet() != null) {
            dto.setStudySetId(game.getStudySet().getId());
        }

        if (game.getCreator() != null) {
            dto.setUserId(game.getCreator().getId());
        }

        return dto;
    }

    // Optional: You could add a fromDTO() if needed later.
}
