package select.music.mapper.user;

import org.springframework.stereotype.Component;
import select.music.domain.user.RefreshToken;
import select.music.domain.user.UserEntity;
import select.music.dto.user.RefreshTokenResponse;

import java.time.Instant;

@Component
public class RefreshTokenMapper {

    public RefreshToken toEntity(String token, UserEntity user, Instant expiresAt) {
        return RefreshToken.builder()
                .token(token)
                .user(user)
                .expiresAt(expiresAt)
                .build();
    }

    public RefreshTokenResponse toResponse(String accessToken, String refreshToken) {
        return new RefreshTokenResponse(accessToken, refreshToken);
    }
}
