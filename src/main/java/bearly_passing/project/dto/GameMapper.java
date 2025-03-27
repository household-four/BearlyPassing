package bearly_passing.project.dto;

import bearly_passing.project.domain.Game;

public class GameMapper {

    public static GameDTO toDTO(Game game) {
        return new GameDTO(
            game.getId(),
            game.getGameType(),
            game.getStudySet() != null ? game.getStudySet().getId() : null,
            game.getUser() != null ? game.getUser().getId() : null
        );
    }

    // Optionally add toEntity(...) later
}
