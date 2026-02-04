package select.music.dto.user;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {}
