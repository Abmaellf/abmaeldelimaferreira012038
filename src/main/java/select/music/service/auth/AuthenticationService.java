package select.music.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import select.music.domain.user.UserEntity;
import select.music.dto.auth.AuthenticationRequestDTO;
import select.music.dto.auth.AuthenticationResponseDTO;
import select.music.infra.security.TokenService;
import select.music.repository.user.UserRepository;
import select.music.service.user.RefreshTokenService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
        try {
            //Authentica com os dados da request
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.login(), requestDTO.password())
            );

            //Busca o usuario logado
            UserEntity principal = (UserEntity) auth.getPrincipal();

            //Valida no banco de dados
            UserEntity user = (UserEntity) userRepository.findByLogin(principal.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado após autenticação"));

            String token = tokenService.generateToken((UserEntity) auth.getPrincipal());
            String refreshToken = refreshTokenService.createRefreshToken((UserEntity) auth.getPrincipal());

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, auth.getAuthorities()));

            AuthenticationResponseDTO base = AuthenticationResponseDTO.from(token, refreshToken, user);
            return new AuthenticationResponseDTO(base.token(), base.refreshToken(), base.userId(), base.email());
        } catch (BadCredentialsException e) {
            log.warn("Credenciais inválidas para email: ", requestDTO.login());
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
