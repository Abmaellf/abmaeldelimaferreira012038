package select.music.controller.album;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.dto.music.MusicRequest;
import select.music.infra.security.RateLimitService;
import select.music.service.album.AlbumService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(
        controllers = AlbumController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "select\\.music\\.infra\\..*"
                ),
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "select\\.music\\.security\\..*"
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlbumService albumService;

    @MockitoBean
    private RateLimitService rateLimitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAlbumSuccessfully() throws Exception {
        // Arrange
        UUID albumId = UUID.randomUUID();
        UUID artistId = UUID.randomUUID();

        MusicRequest musicRequest = new MusicRequest(
                "In The End",
                UUID.randomUUID()
        );
        AlbumRequestDTO request = new AlbumRequestDTO(
                "Hybrid Theory",
                Set.of(artistId),
                Set.of(musicRequest)
        );




        AlbumResponseDTO response = new AlbumResponseDTO(
                albumId,
                "Hybrid Theory",
                Set.of(), // artistas não são o foco do teste
                Set.of(), // artistas não são o foco do teste
                LocalDateTime.now()
        );

        Mockito.when(albumService.create(Mockito.any(AlbumRequestDTO.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/albums")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(albumId.toString()))
                .andExpect(jsonPath("$.name").value("Hybrid Theory"))
                .andExpect(jsonPath("$._links.self.href", notNullValue()));
    }
}

