package select.music.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import select.music.domain.user.RefreshToken;
import select.music.domain.user.UserEntity;
import select.music.dto.user.RefreshTokenRequest;
import select.music.dto.user.RefreshTokenResponse;
import select.music.exception.RefreshTokenExpiredException;
import select.music.exception.RefreshTokenNotFoundException;
import select.music.infra.security.TokenService;
import select.music.infra.util.TokenHashService;
import select.music.mapper.user.RefreshTokenMapper;
import select.music.repository.user.RefreshTokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;
    private final RefreshTokenMapper mapper;
    private final TokenHashService tokenHashService;

    @Value("${spring.api.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    @Transactional
    public String createRefreshToken(UserEntity user) {

        // invalida tokens antigos do usuário
        refreshTokenRepository.deleteByUser(user);

        // 1️⃣ Gera token bruto
        String rawToken = UUID.randomUUID() + UUID.randomUUID().toString();

        // 2️⃣ Hash seguro
        String tokenHash = tokenHashService.hash(rawToken);

        Instant expiresAt = Instant.now().plusMillis(refreshExpirationMs);

        RefreshToken refreshToken = mapper.toEntity(
                tokenHash,
                user,
                expiresAt
        );
        refreshTokenRepository.save(refreshToken);
        return tokenHash;
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.refreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException();
        }

        UserEntity user = refreshToken.getUser();

        String newAccessToken = tokenService.generateToken(user);

        return mapper.toResponse(newAccessToken, refreshToken.getToken());
    }
}

