package select.music.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import select.music.dto.author.AuthorRequest;
import select.music.dto.author.AuthorResponse;
import select.music.exception.AuthorAlreadyExistsException;
import select.music.exception.AuthorNotFoundException;
import select.music.mapper.AuthorMapper;
import select.music.repository.AuthorRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    @Override
    public AuthorResponse create(AuthorRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new AuthorAlreadyExistsException(request.name());
        }

        var entity = mapper.toEntity(request);
        var saved = repository.save(entity);

        return mapper.toResponse(saved);
    }

    @Override
    public AuthorResponse update(UUID id, AuthorRequest request) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        mapper.updateEntityFromRequest(request, entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponse getById(UUID id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        return mapper.toResponse(entity);
    }
}
