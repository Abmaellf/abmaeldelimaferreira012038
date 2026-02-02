package select.music.dto.user;

import select.music.domain.user.RoleType;

public record UserRequestDTO(
        String login,
        String password,
        RoleType role
) {
}
