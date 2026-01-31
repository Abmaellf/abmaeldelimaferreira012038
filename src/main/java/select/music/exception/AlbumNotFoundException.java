package select.music.exception;
import java.util.UUID;

public class AlbumNotFoundException extends BusinessException {

    public AlbumNotFoundException(UUID id) {
        super("Album not found with id: " + id);
    }
}
