package bearly_passing.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bearly_passing.project.data.UserRepository;
import bearly_passing.project.domain.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public <T extends User> T createUser(T user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
