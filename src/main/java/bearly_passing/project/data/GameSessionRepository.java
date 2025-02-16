package bearly_passing.project.data;

import org.springframework.data.jpa.repository.JpaRepository;
import bearly_passing.project.domain.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

}
