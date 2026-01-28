package select.music.exception;

import java.util.UUID;

public class ArtistNotFoundException extends  RuntimeException {
    public ArtistNotFoundException(UUID id) {
        super("Artist nor found with id: " + id);
    }
}
