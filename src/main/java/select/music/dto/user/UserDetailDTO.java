package select.music.dto.user;

import select.music.domain.user.RoleType;

import java.util.UUID;

public record UserDetailDTO(
        UUID id,
        String login,
        String password,
        RoleType role
) {
}

