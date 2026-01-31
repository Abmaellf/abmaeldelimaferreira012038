package select.music.dto.album;

import java.time.LocalDateTime;
import java.util.UUID;

public record AlbumResponseDTO(
        UUID id,
        String name,
        LocalDateTime createdAt
) {
}
