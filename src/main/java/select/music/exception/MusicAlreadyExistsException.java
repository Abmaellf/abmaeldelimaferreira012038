package select.music.exception;

public class MusicAlreadyExistsException extends RuntimeException {
    public MusicAlreadyExistsException(String name, String author) {
        super("Music already exists: " + name + " - " + author);
    }
}
