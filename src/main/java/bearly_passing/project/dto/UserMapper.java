package bearly_passing.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import bearly_passing.project.domain.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole() != null ? user.getRole().toString() : null);
    }

    public static List<UserDTO> toDTOList(List<? extends User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
