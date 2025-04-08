package bearly_passing.project.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import bearly_passing.project.domain.Game;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameRepositoryTest {
    
    @Autowired
    private GameRepository gameRepository;

    private Game game;

    @BeforeEach
    public void init() {
        game = new Game();
    }

    @Test
    public void GameRepository_FindById_ReturnGame() {
        
        gameRepository.save(game);

        Game savedGame = gameRepository.findById(game.getId()).get();

        Assertions.assertThat(savedGame).isNotNull();
        Assertions.assertThat(savedGame.getId()).isGreaterThan(0);
        Assertions.assertThat(game.getId() == savedGame.getId());
        Assertions.assertThat(game.equals(savedGame));
    }
    
}
