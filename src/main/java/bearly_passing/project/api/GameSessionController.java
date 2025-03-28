package bearly_passing.project.api;

import bearly_passing.project.domain.GameSession;
import bearly_passing.project.dto.GameAnswerDTO;
import bearly_passing.project.dto.GameQuestionDTO;
import bearly_passing.project.dto.GameSessionDTO;
import bearly_passing.project.dto.GameSessionMapper;
import bearly_passing.project.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gamesession")
public class GameSessionController {

    @Autowired
    private GameService gameService;

    @GetMapping("/student/{studentId}")
    public List<GameSessionDTO> getSessionsForStudent(@PathVariable Long studentId) {
        List<GameSession> sessions = gameService.getMyGameSessions(studentId);
        return sessions.stream()
                .map(GameSessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/question/{gameSessionId}")
    public ResponseEntity<GameQuestionDTO> getCurrentQuestion(@PathVariable Long gameSessionId) {
        GameQuestionDTO dto = gameService.getCurrentQuestionDTO(gameSessionId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestBody GameAnswerDTO answerDTO) {
        String result = gameService.submitAnswer(
            answerDTO.getGameSessionId(),
            answerDTO.getQuestionId(),
            answerDTO.getSubmittedAnswer()
        );
        return ResponseEntity.ok(result);
    }

    // May expand this later to include updating score, marking complete, etc. as needed
}
