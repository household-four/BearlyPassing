package bearly_passing.project.api;

import bearly_passing.project.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/{studentID}/{studysetID}/game/{gameID}/{questionID}", method = RequestMethod.POST )
    public ResponseEntity gamePlay(
        @PathVariable(value="studentID") long studentID, 
        @PathVariable(value="studysetID") long studysetID, 
        @PathVariable(value="gameID") long gameID, 
        @PathVariable(value="questionID") long questionID,
        @RequestParam(required=false) String answer) {
        if (answer == null) {
            System.out.println("View Question");
            return new ResponseEntity(gameService.viewQuestion(studentID, studysetID, gameID, questionID), HttpStatus.OK);
        }
        System.out.println("Answer Question");
        return new ResponseEntity(gameService.answerQuestion(studentID, studysetID, gameID, questionID, answer), HttpStatus.OK);
    }
    
}
