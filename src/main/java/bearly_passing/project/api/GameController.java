package bearly_passing.project.api;

import bearly_passing.project.domain.Game;
import bearly_passing.project.dto.GameDTO;
import bearly_passing.project.dto.GameMapper;
import bearly_passing.project.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/all")
    public List<GameDTO> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return games.stream()
                .map(GameMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GameDTO getGameById(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        return GameMapper.toDTO(game);
    }

    @PostMapping("/create")
    public GameDTO createGame(@RequestBody Game game) {
        Game savedGame = gameService.saveGame(game);
        return GameMapper.toDTO(savedGame);
    }
}
