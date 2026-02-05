package select.music.exception;

import java.util.UUID;

public class MusicNotFoundException extends RuntimeException {
    public MusicNotFoundException(UUID id) {
        super("Music not found with id: " + id);
    }
}

