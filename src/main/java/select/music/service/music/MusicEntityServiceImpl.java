package select.music.service.music;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import select.music.dto.music.MusicRequest;
import select.music.dto.music.MusicResponse;
import select.music.exception.AuthorNotFoundException;
import select.music.exception.MusicAlreadyExistsException;
import select.music.exception.MusicNotFoundException;
import select.music.mapper.music.MusicMapper;
import select.music.repository.author.AuthorRepository;
import select.music.repository.music.MusicRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicEntityServiceImpl implements MusicEntityService {

        private final MusicRepository musicRepository;
        private final AuthorRepository authorRepository;
        private final MusicMapper mapper;

        @Override
        public MusicResponse create(MusicRequest request) {

            if (musicRepository.existsByNameAndAuthorId(
                    request.name(),
                    request.authorId()
            )) {
                throw new MusicAlreadyExistsException(
                        request.name(),
                        request.authorId().toString()
                );
            }

            var author = authorRepository.findById( request.authorId())
                    .orElseThrow(() -> new AuthorNotFoundException(request.authorId()));

            var entity = mapper.toEntity(request);
            entity.setAuthor(author);

            var saved = musicRepository.save(entity);
            return mapper.toResponse(saved);
        }


    @Override
    public MusicResponse update(UUID id, MusicRequest request) {
        var entity = musicRepository.findById(id)
                .orElseThrow(() -> new MusicNotFoundException(id));

        mapper.updateEntityFromRequest(request, entity);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public MusicResponse getById(UUID id) {
        return musicRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new MusicNotFoundException(id));
    }
}
