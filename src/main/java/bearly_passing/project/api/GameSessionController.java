package bearly_passing.project.api;

import bearly_passing.project.domain.GameSession;
import bearly_passing.project.dto.GameAnswerDTO;
import bearly_passing.project.dto.GameQuestionDTO;
import bearly_passing.project.dto.GameSessionDTO;
import bearly_passing.project.dto.GameSessionMapper;
import bearly_passing.project.dto.StudySetDTO;
import bearly_passing.project.dto.StudySetMapper;
import bearly_passing.project.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/gamesession")
public class GameSessionController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{id}")
    public GameSessionDTO getSessionById(@PathVariable Long id) {
        log.info("Getting game session {}", id);
        return GameSessionMapper.toDTO(gameService.getGameSessionById(id));
    }

    @GetMapping("/student/{studentId}")
    public List<GameSessionDTO> getSessionsForStudent(@PathVariable Long studentId) {
        log.info("Getting sessions for student {}", studentId);
        List<GameSession> sessions = gameService.getMyGameSessions(studentId);
        return sessions.stream()
                .map(GameSessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/question/{gameSessionId}")
    public ResponseEntity<GameQuestionDTO> getCurrentQuestion(@PathVariable Long gameSessionId) {
        log.info("Getting current question set for session {}", gameSessionId);
        GameQuestionDTO dto = gameService.getCurrentQuestionDTO(gameSessionId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/answer")
    public ResponseEntity<Map<String, String>> submitAnswer(@RequestBody GameAnswerDTO answerDTO) {
        Map<String, String> result = gameService.submitAnswer(
                answerDTO.getGameSessionId(),
                answerDTO.getQuestionId(),
                answerDTO.getSubmittedAnswer());
        log.info("Submitting answer");
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("delete/{gameSessionId}")
    public ResponseEntity<Void> deleteGameSession(
            @PathVariable Long gameSessionId) {

        log.info("Deleting game session for game {} and student {}", gameSessionId);
        gameService.deleteSession(gameSessionId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // May expand this later to include updating score, marking complete, etc. as
    // needed
}
