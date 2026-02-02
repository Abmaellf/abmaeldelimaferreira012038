package select.music.dto.user;

import select.music.domain.user.RoleType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String login,
        RoleType role,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
