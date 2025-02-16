package bearly_passing.project.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import bearly_passing.project.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(Long id);
}
