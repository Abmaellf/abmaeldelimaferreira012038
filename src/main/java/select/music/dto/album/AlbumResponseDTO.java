package select.music.dto.album;

import select.music.domain.artist.ArtistEntity;
import select.music.dto.music.MusicResponse;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AlbumResponseDTO(
        UUID id,
        String name,
        Set<ArtistEntity> artists,
        Set<MusicResponse> musics, // ✅ AQUI
        LocalDateTime createdAt
) {
    public AlbumResponseDTO {
        musics = musics == null ? Set.of() : musics;
    }
}
