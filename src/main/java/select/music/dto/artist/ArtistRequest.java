package select.music.dto.artist;

import select.music.domain.artist.ArtistType;

import java.time.LocalDate;

public record ArtistRequest(
        String name,
        ArtistType type,
        LocalDate foundation
) {
}
