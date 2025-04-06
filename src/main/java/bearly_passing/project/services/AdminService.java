package bearly_passing.project.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.QuestionRepository;
import bearly_passing.project.data.StudySetRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Game;
import bearly_passing.project.domain.Question;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.StudySet;
import bearly_passing.project.domain.Teacher;
import jakarta.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudySetRepository studySetRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Transactional
    public void populateDummyData() {
        gameRepository.deleteAll();
        gameSessionRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
        studySetRepository.deleteAll();

        if (userRepository.count() > 0) {
            System.out.println("Users already exist. Skipping population.");
            return;
        }

        Teacher teacher1 = new Teacher();
        teacher1.setName("Mr. Smith");

        Teacher teacher2 = new Teacher();
        teacher2.setName("Ms. Johnson");

        Teacher teacher3 = new Teacher();
        teacher3.setName("Dr. Brown");

        Student student1 = new Student();
        student1.setName("Christen");
        student1.setGrade(100.0f);

        Student student2 = new Student();
        student2.setName("Matthew");
        student1.setGrade(100.0f);

        Student student3 = new Student();
        student3.setName("Kevin");
        student1.setGrade(100.0f);

        Student student4 = new Student();
        student4.setName("Faizan");
        student1.setGrade(100.0f);

        Student student5 = new Student();
        student5.setName("Joshua");
        student1.setGrade(100.0f);

        teacher1.setStudents(Arrays.asList(student1, student2));
        teacher2.setStudents(Arrays.asList(student3, student4));
        teacher3.setStudents(Arrays.asList(student5));

        userRepository.saveAll(Arrays.asList(student1, student2, student3, student4, student5));
        userRepository.saveAll(Arrays.asList(teacher1, teacher2, teacher3));

        Question question = new Question();
        question.setBody("What color is the sky?");
        question.setCorrectAnswer("blue");

        StudySet studySet = new StudySet();
        studySet.getQuestions().add(question);

        Game game = new Game();
        game.setStudySet(studySet);

        question.setStudySet(studySet);
        studySet.getGames().add(game);

        questionRepository.save(question);
        studySetRepository.save(studySet);
        gameRepository.save(game);

        System.out.println("Dummy data populated successfully!");
    }

    @Transactional
    public String studentGrade(Long studentID) {
        Student student = (Student) userRepository.findById(studentID)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getName() + ": " + student.getGrade();
    }

    @Transactional
    public String teacherClass(Long teacherID) {
        Teacher teacher = (Teacher) userRepository.findById(teacherID)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        String out = "Teacher: " + teacher.getName() + "\n\nStudents:\n";

        for (Student student : teacher.getStudents()) {
            out += student.getName() + "\n";
        }

        return out;
    }

}
