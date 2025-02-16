package bearly_passing.project.api;

import bearly_passing.project.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class GameController {

    private UserService userService;

    @Autowired
    public GameController(UserService userService) {
        this.userService = userService;
    }

    public enum GameType {
        MATCHING, AUDIO, QUIZ
    }

    // Return list of game types
    // @GetMapping("/game")
    // public String home() {
    //     return "home";
    // }
    
    // Return list of game types
    // @GetMapping("/game")
    @RequestMapping(value = "/game/{ID}/{TYPE}", method = RequestMethod.POST )
    public ResponseEntity gamePlay(@PathVariable(value="ID") long studysetID, @PathVariable(value="TYPE") String gameType) {
        return new ResponseEntity(userService.takeQuiz(studysetID, gameType), HttpStatus.OK);
    }

}
