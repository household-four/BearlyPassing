package bearly_passing.project.api;

import bearly_passing.project.domain.Game;
import bearly_passing.project.dto.GameDTO;
import bearly_passing.project.dto.GameMapper;
import bearly_passing.project.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
@CrossOrigin
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/all")
    public List<GameDTO> getAllGames() {
        return gameService.getAllGames().stream()
                .map(GameMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public GameDTO getGameById(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        return GameMapper.toDTO(game);
    }

    @PostMapping("/create")
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO) {
        Game saved = gameService.createNewGame(
            gameDTO.getStudySetId(),
            gameDTO.getUserId(),
            gameDTO.getGameType()
        );
        return ResponseEntity.ok(GameMapper.toDTO(saved));
    }

    @PostMapping("/test")
    public String testPost() {
        return "Test OK";
    }
}
