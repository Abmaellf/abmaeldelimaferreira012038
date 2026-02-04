package select.music.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import select.music.dto.user.RefreshTokenRequest;
import select.music.dto.user.RefreshTokenResponse;
import select.music.service.user.RefreshTokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
            @RequestBody RefreshTokenRequest request
    ) {
        RefreshTokenResponse response = refreshTokenService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}

