package bearly_passing.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import bearly_passing.project.domain.UserRole;

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

    public <T extends User> T saveUser(T user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

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

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public Student createStudent(Student student) {
        student.setRole(UserRole.STUDENT);
        return userRepository.save(student);
    }

    public Teacher createTeacher(Teacher teacher) {
        teacher.setRole(UserRole.TEACHER);
        return userRepository.save(teacher);
    }
}
