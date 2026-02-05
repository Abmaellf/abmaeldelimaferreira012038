package select.music.event.album;

import select.music.dto.album.AlbumResponseDTO;

public record AlbumCreatedEvent(AlbumResponseDTO album) {
}
