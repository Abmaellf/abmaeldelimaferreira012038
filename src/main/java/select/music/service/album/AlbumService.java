package select.music.service.album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import select.music.domain.artist.ArtistType;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface AlbumService {

    AlbumResponseDTO create(AlbumRequestDTO request);

    AlbumResponseDTO findById(UUID id);

    Page<AlbumResponseDTO> findAll(Pageable pageable);

    Page<AlbumResponseDTO> findAllByArtist(UUID artistId, Pageable pageable);

    Page<AlbumResponseDTO> findAllWithFilters(
            Set<ArtistType> artistTypes,
            LocalDate createdAfter,
            Pageable pageable
    );

    AlbumResponseDTO update(UUID id, AlbumRequestDTO request);

    void delete(UUID id);
}
