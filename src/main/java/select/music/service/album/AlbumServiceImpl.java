package select.music.service.album;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import select.music.domain.album.AlbumEntity;
import select.music.domain.album.AlbumImageEntity;
import select.music.domain.artist.ArtistEntity;
import select.music.domain.artist.ArtistType;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.exception.ArtistNotFoundException;
import select.music.mapper.music.AlbumMapper;
import select.music.repository.album.AlbumImageRepository;
import select.music.repository.album.AlbumRepository;
import select.music.repository.artist.ArtistRepository;
import select.music.service.storage.MinioStorageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;
    private final AlbumImageRepository albumImageRepository;
    private final MinioStorageService minioStorageService;

    public List<String> listPresignedImages(UUID albumId) {
        List<AlbumImageEntity> images =
                albumImageRepository.findByAlbumId(albumId);

        return images.stream()
                .map(img -> minioStorageService.generatePresignedUrl(img.getObjectKey()))
                .toList();
    }


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
    public Page<AlbumResponseDTO> findAllByArtist(UUID artistId, Pageable pageable) {

        // valida se o artista existe (regra de negócio)
        artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));

        return albumRepository.findAllByArtistId(artistId, pageable)
                .map(albumMapper::toPageResponse);
    }

    @Override
    public Page<AlbumResponseDTO> findAll(Pageable pageable) {

        return albumRepository.findAll(pageable)
                .map(albumMapper::toPageResponse);
    }

    @Override
    public Page<AlbumResponseDTO> findAllWithFilters(
            Set<ArtistType> artistTypes,
            LocalDate createdAfter,
            Pageable pageable
    ) {
        return albumRepository.findAllWithFilters(
                artistTypes == null || artistTypes.isEmpty()
                        ? null
                        : artistTypes,
                createdAfter == null
                        ? null
                        : createdAfter.atStartOfDay(),
                pageable
        ).map(albumMapper::toPageResponse);
    }

    @Override
    public AlbumResponseDTO update(UUID id, AlbumRequestDTO request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}

