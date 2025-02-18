package bearly_passing.project.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import bearly_passing.project.domain.User;
import bearly_passing.project.services.AdminService;
import bearly_passing.project.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/populate")
    public ResponseEntity<String> populateDummyData() {
        adminService.populateDummyData();
        return ResponseEntity.ok("Dummy data populated successfully!");
    }

    @GetMapping("/grades")
    public ResponseEntity getStudentGrade(@RequestParam Long studentID) {
        return new ResponseEntity(adminService.studentGrade(studentID), HttpStatus.OK);
    }

    @GetMapping("/class")
    public ResponseEntity getClass(@RequestParam Long teacherID) {
        return new ResponseEntity(adminService.teacherClass(teacherID), HttpStatus.OK);
    }

}
