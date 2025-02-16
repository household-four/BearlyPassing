package bearly_passing.project.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.GameRepository;
import bearly_passing.project.data.GameSessionRepository;
import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.Student;
import bearly_passing.project.domain.Teacher;
import jakarta.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

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

        // TODO: Make questions, studysets, games, and assign them to users

        System.out.println("Dummy data populated successfully!");
    }
}
