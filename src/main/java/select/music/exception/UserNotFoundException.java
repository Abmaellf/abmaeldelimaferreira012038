package select.music.exception;

import select.music.domain.user.BusinessException;

import java.util.UUID;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(UUID id) {
        super("User not found with id: " + id);
    }

    public UserNotFoundException(String login) {
        super("User not found with login: " + login);
    }
}

