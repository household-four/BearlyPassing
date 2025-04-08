package bearly_passing.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.User;
import bearly_passing.project.domain.UserRole;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameSessionRepository gameSessionRepository;

    @InjectMocks
    private UserService userService;

    private Student student;
    private Student student2;
    private Student student3;

    private ArrayList<User> users;

    @BeforeEach
    public void init() {
        student = new Student();
            student.setId(11111111L);
            student.setUsername("Baylor");
            student.setRole(UserRole.STUDENT);

        student2 = new Student();
            student2.setId(22222222L);
            student2.setUsername("Bear");
            student2.setRole(UserRole.STUDENT);

        student3 = new Student();
            student3.setId(33333333L);
            student3.setUsername("Green");
            student3.setRole(UserRole.STUDENT);

        users = new ArrayList<>();
        users.add(student);
        users.add(student2);
        users.add(student3);
    }

    @Test
    public void UserService_SaveUser_ReturnsSavedUser() {

        when(userRepository.save(Mockito.any(User.class))).thenReturn(student);

        User savedUser = userService.saveUser(student);

        Assertions.assertThat(savedUser).isNotNull();
        assertEquals(student, savedUser);
    }

    @Test
    public void UserServce_GetAllUsers_ReturnsListUsers() {

        when(userRepository.findAll()).thenReturn(users);

        List<User> savedUsers = userService.getAllUsers();

        Assertions.assertThat(savedUsers).isNotNull();
        assertEquals(users, savedUsers);
    }
}
