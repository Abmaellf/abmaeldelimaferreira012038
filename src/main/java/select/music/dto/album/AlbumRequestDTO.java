package select.music.dto.album;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record AlbumRequestDTO(@NotBlank String name,  Set<UUID> artistIds) {
}
