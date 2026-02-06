package select.music.dto.artist;

import select.music.domain.artist.ArtistType;

public record ArtistRequest(
        String name,
        ArtistType type
) {
}
