package select.music.exception;

/* 409 */
public class AuthorAlreadyExistsException extends RuntimeException {
    public AuthorAlreadyExistsException(String name) {
        super("Author already exists with name: " + name);
    }
}
