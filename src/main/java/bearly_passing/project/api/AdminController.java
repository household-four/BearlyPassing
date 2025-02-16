package bearly_passing.project.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bearly_passing.project.domain.User;
import bearly_passing.project.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/populate")
    public ResponseEntity<String> populateDummyData() {
        userService.populateDummyData();
        return ResponseEntity.ok("Dummy data populated successfully!");
    }

}
