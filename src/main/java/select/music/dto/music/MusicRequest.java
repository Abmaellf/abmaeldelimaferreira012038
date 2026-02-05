package select.music.dto.music;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MusicRequest(
        @NotBlank String name,
        @NotNull UUID authorId
) {
}
