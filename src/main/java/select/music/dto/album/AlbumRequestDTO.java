package select.music.dto.album;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

@Schema(
        name = "AlbumRequest",
        description = "Dados para criação ou atualização de um álbum"
)
public record AlbumRequestDTO(
        @Schema(
                description = "Nome do álbum",
                example = "Hybrid Theory",
                minLength = 1,
                maxLength = 150
        )
        @NotBlank
        @Size(max = 150)
        String name,

        @Schema(
                description = "IDs dos artistas associados ao álbum",
                example = "[\"550e8400-e29b-41d4-a716-446655440000\"]"
        )
        @NotEmpty
        Set<UUID> artistIds) {
}
