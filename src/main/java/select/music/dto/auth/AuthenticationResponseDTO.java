package select.music.dto.auth;

import select.music.domain.user.UserEntity;

import java.util.UUID;

public record AuthenticationResponseDTO(
                String token,
                String refreshToken,
                UUID userId,
                String email
        ) {

        public static AuthenticationResponseDTO from( String token, String refreshToken, UserEntity user) {
                return new AuthenticationResponseDTO(
                        token,
                        refreshToken,
                        user.getId(),
                        user.getLogin()
                );
        }
}
