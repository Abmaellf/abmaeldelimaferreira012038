package select.music.service.album;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import select.music.domain.album.AlbumEntity;
import select.music.domain.artist.ArtistEntity;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.exception.ArtistNotFoundException;
import select.music.mapper.music.AlbumMapper;
import select.music.repository.AlbumRepository;
import select.music.repository.artist.ArtistRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;

    @Override
    public AlbumResponseDTO create(AlbumRequestDTO request) {

        AlbumEntity albumEntity = albumMapper.toEntity(request);

        Set<ArtistEntity> artistEntitySet = request.artistIds().stream()
                .map(id -> artistRepository.findById(id)
                        .orElseThrow(() -> new ArtistNotFoundException(id))
                )
                .collect(Collectors.toSet());

        albumEntity.setArtists(artistEntitySet);

        AlbumEntity saved = albumRepository.save(albumEntity);

        return albumMapper.toResponse(saved);
    }

    @Override
    public AlbumResponseDTO findById(UUID id) {
        return albumMapper.toResponse(albumRepository.findById(id).orElseThrow());
    }

    @Override
    public Page<AlbumResponseDTO> findAll(Pageable pageable) {

        return albumRepository.findAll(pageable)
                .map(albumMapper::toPageResponse);
    }

    @Override
    public AlbumResponseDTO update(UUID id, AlbumRequestDTO request) {
        AlbumEntity albumEntity = albumRepository.findById(id).orElseThrow();
        albumEntity.setName(request.name());
        return albumMapper.toResponse(albumRepository.save(albumEntity));
    }

    @Override
    public void delete(UUID id) {
        albumRepository.delete(albumRepository.findById(id).orElseThrow());

    }
}
