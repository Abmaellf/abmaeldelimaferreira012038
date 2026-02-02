package select.music.exception;

import select.music.domain.user.BusinessException;

public class LoginAlreadyExistsException extends BusinessException {

    public LoginAlreadyExistsException(String login) {
        super("Login already exists: " + login);
    }
}

