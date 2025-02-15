package bearly_passing.project.data;

import org.springframework.data.jpa.repository.JpaRepository;

import bearly_passing.project.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
