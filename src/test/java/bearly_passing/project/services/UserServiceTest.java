package bearly_passing.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
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
    private Teacher teacher;

    private List<User> users;

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

        teacher = new Teacher();
            teacher.setId(44444444L);
            teacher.setName("Gold");

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

    @Test
    public void UserService_GetUserById_ReturnsUser() {
        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));

        User savedUser = userService.getUserById(student.getId());

        assertEquals(student, savedUser);
    }

    @Test
    public void UserService_CreateStudent_ReturnsStudent() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(student);

        User savedUser = userService.createStudent(student);

        assertEquals(student, savedUser);
    }

    @Test
    public void UserService_CreateTeacher_ReturnsTeacher() {

        when(userRepository.save(Mockito.any(User.class))).thenReturn(teacher);

        User savedUser = userService.createTeacher(teacher);

        assertEquals(teacher, savedUser);
    }

    @Test
    public void UserService_UpdateUser_ReturnsUser() {
        
        student.setName("John");

        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(student);

        User savedUser = userService.updateUser(student.getId(), student);

        assertEquals(student, savedUser);

    }

    @Test
    public void UserService_DeleteUser_ReturnVoid() {
        doNothing().when(userRepository).deleteById(student.getId());

        assertAll(() -> userService.deleteUser(student.getId()));
    }

    // add student to a teacher

    // assign game to student

    @Test
    public void UserService_GetStudentsByTeacherId_ReturnsStudents() {

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);
        teacher.setStudents(students);

        when(userRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        List<Student> savedStudents = userService.getStudentsByTeacherId(teacher.getId());

        assertEquals(students, savedStudents);
    }

    @Test
    public void UserService_GetTeachersByStudentId_ReturnsTeachers() {

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        student.setTeachers(teachers);

        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));

        List<Teacher> savedTeachers = userService.getTeachersByStudentId(student.getId());

        assertEquals(teachers, savedTeachers);
    }

    @Test
    public void UserService_GetStudySetsById_ReturnsStudySets() {
        List<StudySet> sets = new ArrayList<>();
        StudySet set = new StudySet();
        set.setTitle("Bearly Passing");
        set.setCreator(student);
        set.setDescription("How to pass every class.");
        sets.add(set);
        student.setStudySets(sets);

        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));
        
        List<StudySet> savedStudySets = userService.getStudySetsById(student.getId());

        assertEquals(sets, savedStudySets);
    }
}
