package select.music.dto.music;

import java.time.LocalDateTime;
import java.util.UUID;

public record MusicResponse(
        UUID id,
        String name,
        UUID authorId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

