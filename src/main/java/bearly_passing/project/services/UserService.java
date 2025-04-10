package bearly_passing.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.*;

import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    // Create generic user
    public <T extends User> T saveUser(T user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all students
    public List<User> getAllStudents() {
        return userRepository.findByRole(UserRole.STUDENT);
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Create new student with role
    public Student createStudent(Student student) {
        student.setRole(UserRole.STUDENT);
        return userRepository.save(student);
    }

    // Create new teacher with role
    public Teacher createTeacher(Teacher teacher) {
        teacher.setRole(UserRole.TEACHER);
        return userRepository.save(teacher);
    }

    // Update existing user
    public User updateUser(Long id, User userUpdate) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existing.setUsername(userUpdate.getUsername());
        existing.setRole(userUpdate.getRole());
        return userRepository.save(existing);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Assign a student to a teacher
    @Transactional
    public Teacher addStudentToTeacher(Long teacherId, Long studentId) {
        Teacher teacher = (Teacher) userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Student student = (Student) userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!teacher.getStudents().contains(student)) {
            teacher.getStudents().add(student);
            student.getTeachers().add(teacher);
            userRepository.save(teacher);
        }

        return teacher;
    }

    // Assign game to student
    @Transactional
    public GameSession assignGameToStudent(Long teacherId, Long studentId, Long gameId) {
        Teacher teacher = (Teacher) userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Student student = (Student) userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (!teacher.getStudents().contains(student)) {
            throw new RuntimeException("Student is not assigned to this teacher");
        }

        GameSession gameSession = new GameSession();
        gameSession.setGame(game);
        gameSession.setStudent(student);
        gameSession.setScore(0);
        gameSession.setCompleted(false);

        gameSession = gameSessionRepository.save(gameSession);
        student.getAssignedGames().add(gameSession);
        userRepository.save(student);

        return gameSession;
    }

    @Transactional
    public List<Student> getStudentsByTeacherId(Long teacherId) {
        Teacher teacher = (Teacher) userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return teacher.getStudents();
    }

    @Transactional
    public List<Teacher> getTeachersByStudentId(Long studentId) {
        Student student = (Student) userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getTeachers();
    }

    @Transactional
    public List<StudySet> getStudySetsById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getStudySets();
    }

}
