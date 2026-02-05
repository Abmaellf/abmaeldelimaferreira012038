package select.music.service.author;

import select.music.dto.author.AuthorRequest;
import select.music.dto.author.AuthorResponse;

import java.util.UUID;


public interface AuthorService {

    AuthorResponse create(AuthorRequest request);

    AuthorResponse update(UUID id, AuthorRequest request);

    AuthorResponse getById(UUID id);
}

