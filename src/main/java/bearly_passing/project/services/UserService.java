package bearly_passing.project.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public <T extends User> T createUser(T user) {
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
            teacher.getStudents().add(student); // Do we need both of these `add` methods?
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

        // Creates a new GameSession using the Game the teacher created
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
    public void populateDummyData() {
        Teacher teacher1 = new Teacher();
        teacher1.setName("Mr. Smith");

        Teacher teacher2 = new Teacher();
        teacher2.setName("Ms. Johnson");

        Teacher teacher3 = new Teacher();
        teacher3.setName("Dr. Brown");

        Student student1 = new Student();
        student1.setName("Christen");

        Student student2 = new Student();
        student2.setName("Matthew");

        Student student3 = new Student();
        student3.setName("Kevin");

        Student student4 = new Student();
        student4.setName("Faizan");

        Student student5 = new Student();
        student5.setName("Joshua");

        teacher1.setStudents(Arrays.asList(student1, student2));
        teacher2.setStudents(Arrays.asList(student3, student4));
        teacher3.setStudents(Arrays.asList(student5));

        userRepository.saveAll(Arrays.asList(student1, student2, student3, student4, student5));
        userRepository.saveAll(Arrays.asList(teacher1, teacher2, teacher3));
        System.out.println("Dummy data populated successfully!");
    }
}
