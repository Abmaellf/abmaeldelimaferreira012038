package select.music.service.artist;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import select.music.dto.artist.ArtistRequest;
import select.music.dto.artist.ArtistResponse;
import select.music.repository.artist.ArtistRepository;

import java.util.UUID;


public interface ArtistService {
    ArtistResponse create(ArtistRequest request);

    ArtistResponse update(UUID id, ArtistRequest request);

    ArtistResponse findById(UUID id);

    ArtistResponse findByName(String name);
}
