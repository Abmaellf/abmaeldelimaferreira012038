package select.music.dto.album;

import select.music.domain.artist.ArtistEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AlbumResponseDTO(
        UUID id,
        String name,
        Set<ArtistEntity> artists,
        LocalDateTime createdAt
) {
}
