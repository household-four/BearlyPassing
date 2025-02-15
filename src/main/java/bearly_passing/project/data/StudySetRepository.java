package bearly_passing.project.data;

import org.springframework.data.jpa.repository.JpaRepository;

import bearly_passing.project.domain.StudySet;

public interface StudySetRepository extends JpaRepository<StudySet, Long> {

}
