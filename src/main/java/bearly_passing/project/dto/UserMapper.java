package bearly_passing.project.dto;

import bearly_passing.project.domain.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getRole() != null ? user.getRole().toString() : null
        );
    }
}
