package select.music.dto.album;

import jakarta.validation.constraints.NotBlank;

public record AlbumRequestDTO(@NotBlank String name) {
}
