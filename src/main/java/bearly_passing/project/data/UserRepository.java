package bearly_passing.project.data;

import org.springframework.data.jpa.repository.JpaRepository;

import bearly_passing.project.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
