package select.music.service.album;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import select.music.domain.album.AlbumEntity;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.mapper.music.AlbumMapper;
import select.music.repository.AlbumRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    @Override
    public AlbumResponseDTO create(AlbumRequestDTO request) {
        AlbumEntity albumEntity = albumMapper.toEntity(request);
        return albumMapper.toResponse(albumRepository.save(albumEntity));
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
