package bearly_passing.project.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import bearly_passing.project.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
