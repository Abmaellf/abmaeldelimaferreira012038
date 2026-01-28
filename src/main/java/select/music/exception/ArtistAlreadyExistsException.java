package select.music.exception;

public class ArtistAlreadyExistsException extends  RuntimeException {
    public ArtistAlreadyExistsException( String name) {
        super("Artist already exists with name "+name);
    }
}
