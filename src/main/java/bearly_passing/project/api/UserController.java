package bearly_passing.project.api;

import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.dto.UserDTO;
import bearly_passing.project.dto.UserMapper;
import bearly_passing.project.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return UserMapper.toDTO(user);
    }

    @PostMapping("/student/create")
    public UserDTO createStudent(@RequestBody Student student) {
        Student saved = userService.createStudent(student);
        return UserMapper.toDTO(saved);
    }

    @PostMapping("/teacher/create")
    public UserDTO createTeacher(@RequestBody Teacher teacher) {
     Teacher saved = userService.createTeacher(teacher);
        return UserMapper.toDTO(saved);
    }

}
