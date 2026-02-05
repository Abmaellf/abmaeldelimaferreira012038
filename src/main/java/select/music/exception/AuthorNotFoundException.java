package select.music.exception;

import java.util.UUID;

/* 404 */
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(UUID id) {
        super("Author not found with id: " + id);
    }
}

