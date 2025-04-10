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
import bearly_passing.project.domain.GameSession;
import bearly_passing.project.domain.GameType;
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
        question.setBody("What is the star at the center of our universe?");
        question.setCorrectAnswer("sun");
        question.setDifficulty(Question.Difficulty.EASY);

        Question question2 = new Question();
        question2.setBody("What part of a plant allows it to make its own food?");
        question2.setCorrectAnswer("leaf");
        question2.setDifficulty(Question.Difficulty.MEDIUM);

        Question question3 = new Question();
        question3.setBody("What do humans need to breathe?");
        question3.setCorrectAnswer("oxygen");
        question3.setDifficulty(Question.Difficulty.EASY);

        Question question4 = new Question();
        question4.setBody("Which season is the coldest?");
        question4.setCorrectAnswer("winter");
        question4.setDifficulty(Question.Difficulty.EASY);

        StudySet studySet = new StudySet();
        studySet.setTitle("1st Grade Science");
        studySet.setDescription("Basic Science Questions for 1st Graders");
        question.setStudySet(studySet);
        question2.setStudySet(studySet);
        question3.setStudySet(studySet);
        question4.setStudySet(studySet);
        // studySet.getQuestions().add(question);
        // studySet.getQuestions().add(question2);

        Game game = new Game();
        game.setStudySet(studySet);
        game.setCreator(teacher1);
        game.setGameType(GameType.MATCHING);

        Game game2 = new Game();
        game2.setStudySet(studySet);
        game2.setCreator(teacher1);
        game2.setGameType(GameType.FLASHCARD);

        GameSession gameSession1 = createDummyGameSession(student1, teacher1, game);
        GameSession gameSession2 = createDummyGameSession(student2, teacher1, game);
        GameSession gameSession3 = createDummyGameSession(student1, teacher1, game2);
        GameSession gameSession4 = createDummyGameSession(student2, teacher1, game2);
        gameSessionRepository.save(gameSession1);
        gameSessionRepository.save(gameSession2);
        gameSessionRepository.save(gameSession3);
        gameSessionRepository.save(gameSession4);

        // question.setStudySet(studySet);
        studySet.getGames().add(game);

        studySet.setCreator(teacher1);
        questionRepository.save(question);
        questionRepository.save(question2);
        questionRepository.save(question3);
        questionRepository.save(question4);
        studySetRepository.save(studySet);
        gameRepository.save(game);
        gameRepository.save(game2);

        System.out.println("Dummy data populated successfully!");
    }

    GameSession createDummyGameSession(Student student, Teacher creator, Game game) {
        GameSession gameSession = new GameSession();
        gameSession.setStudent(student);
        gameSession.setGame(game);
        gameSession.setCompleted(false);
        gameSession.setScore(0);
        gameSession.setCurrentQuestionIndex(0);
        return gameSession;
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
