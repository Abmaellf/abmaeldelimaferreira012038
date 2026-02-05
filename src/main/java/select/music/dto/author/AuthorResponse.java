package select.music.dto.author;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}