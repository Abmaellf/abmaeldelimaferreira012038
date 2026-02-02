package select.music.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import select.music.exception.ArtistAlreadyExistsException;
import select.music.exception.ArtistNotFoundException;
import select.music.exception.LoginAlreadyExistsException;
import select.music.exception.UserNotFoundException;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExeceptionHandler {

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ArtistNotFoundException ex,
            HttpServletRequest request
        ){
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body(new ApiError(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            ex.getMessage(),
                            request.getRequestURI(),
                            LocalDateTime.now()
                    ));
    }

    @ExceptionHandler(ArtistAlreadyExistsException.class)
    public ResponseEntity<ApiError> handlerConflit(
            ArtistAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserConflict(
            LoginAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

}
