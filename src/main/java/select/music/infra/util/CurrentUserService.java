package select.music.infra.util;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import select.music.domain.user.UserEntity;
import select.music.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser(Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserEntity userDetails) {
            return userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        }

        throw new AccessDeniedException("Tipo de principal não suportado");
    }

    /**
     * Retorna o ID do usuário autenticado (útil para logs ou auditoria).
     */
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userDetails) {
            return userDetails.getId().toString();
        }

        throw new AccessDeniedException("Tipo de principal não suportado");
    }

    private UserEntity getUserDetailsFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userDetails) {
            return userDetails;
        }

        throw new AccessDeniedException("Tipo de principal não suportado");
    }
}