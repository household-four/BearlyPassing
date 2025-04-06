package bearly_passing.project.api;

import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.dto.StudySetDTO;
import bearly_passing.project.dto.StudySetMapper;
import bearly_passing.project.dto.UserDTO;
import bearly_passing.project.dto.UserMapper;
import bearly_passing.project.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // READ: Get all users
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ: Get a user by ID
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return UserMapper.toDTO(userService.getUserById(id));
    }

    // CREATE: New student
    @PostMapping("/student/create")
    public UserDTO createStudent(@RequestBody Student student) {
        return UserMapper.toDTO(userService.createStudent(student));
    }

    // CREATE: New teacher
    @PostMapping("/teacher/create")
    public UserDTO createTeacher(@RequestBody Teacher teacher) {
        return UserMapper.toDTO(userService.createTeacher(teacher));
    }

    // UPDATE: Existing user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User userUpdate) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.updateUser(id, userUpdate)));
    }

    // DELETE: User by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/my-study-sets/{id}")
    public List<StudySetDTO> getStudySetsById(@PathVariable Long id) {
        return StudySetMapper.toDTOList(userService.getStudySetsById(id));
    }
}
