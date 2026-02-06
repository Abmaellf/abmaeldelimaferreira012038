package select.music.service.artist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import select.music.domain.artist.ArtistEntity;
import select.music.domain.artist.ArtistType;
import select.music.dto.artist.ArtistRequest;
import select.music.dto.artist.ArtistResponse;
import select.music.exception.ArtistAlreadyExistsException;
import select.music.exception.ArtistNotFoundException;
import select.music.mapper.artist.ArtistMapper;
import select.music.repository.artist.ArtistRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtistServiceImp implements  ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    @Override
    public List<ArtistResponse> findAll() {

       return artistMapper.toResponseList(artistRepository.findAll());
    }

    @Override
    public ArtistResponse create(ArtistRequest artistRequest) {
        artistRepository.findByNameIgnoreCase(artistRequest.name())
                .ifPresent(a -> {
                    throw new ArtistAlreadyExistsException(artistRequest.name());
                });

        ArtistEntity entity = artistMapper.toEntity(artistRequest);
//        if (entity.getType() == null) {
//            entity.setType(ArtistType.SOLO);
//        }
        System.out.println(entity.getType());
        ArtistEntity saved = artistRepository.save(entity);

        return artistMapper.toResponse(saved);
    }

    @Override
    public ArtistResponse update(UUID id, ArtistRequest artistRequest) {
        ArtistEntity entity = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(id));

        artistMapper.updateEntity(entity, artistRequest);
        return artistMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistResponse findById(UUID id) {
        return artistRepository.findById(id)
                .map(artistMapper::toResponse)
                .orElseThrow(()-> new ArtistNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistResponse findByName(String name) {
        return artistRepository.findByNameIgnoreCase(name)
                .map(artistMapper::toResponse)
                .orElseThrow(()-> new ArtistAlreadyExistsException(name));
    }

}
