package bearly_passing.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.Teacher;
import bearly_passing.project.domain.User;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

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
    public List<Question> takeQuiz(long studysetID, String gameType) {
        return em.createQuery("SELECT g.studyset_id FROM Game g WHERE g.studyset_id = :id AND g.type = :type", Question.class)
            .setParameter("id", studysetID)
            .setParameter("type", gameType)
            .getResultList().get(0);
    }

}
