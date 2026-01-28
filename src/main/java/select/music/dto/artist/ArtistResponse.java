package select.music.dto.artist;

import select.music.domain.artist.ArtistType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ArtistResponse(
        UUID id,
        String name,
        ArtistType type,
        LocalDate foundation,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
