package select.music.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import select.music.infra.util.ClientIpAddressService;
import select.music.infra.util.CurrentUserService;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final CurrentUserService currentUserService;
    private final ClientIpAddressService clientIpAddressService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String key = resolveRateLimitKey(request);
        Bucket bucket = rateLimitService.resolveBucket(key);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(429);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of(
                                "status", 429,
                                "error", "Too Many Requests",
                                "message", "Limite de 10 requisições por minuto excedido"
                        )
                )
        );
    }

    private String resolveRateLimitKey(HttpServletRequest request) {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

                String userId = currentUserService.getCurrentUserId();
                return "user:" + userId;
            }
        } catch (Exception ignored) {
        }

        String ip = clientIpAddressService.getClientIpAddress(request);
        return "ip:" + ip;
    }
}
