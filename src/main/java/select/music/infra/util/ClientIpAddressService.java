package select.music.infra.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
public class ClientIpAddressService {

    public String getClientIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "UNKNOWN";
        }

        // Lista de cabeçalhos comuns em balanceadores, proxies e CDNs
        String[] headersToCheck = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String header : headersToCheck) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
                // Pode conter múltiplos IPs -> pega o primeiro válido
                String firstIp = ip.split(",")[0].trim();
                return normalizeIp(firstIp);
            }
        }

        return normalizeIp(request.getRemoteAddr());
    }

    private String normalizeIp(String ip) {
        if (ip == null || ip.isBlank()) {
            return "UNKNOWN";
        }

        // Remove zone index de IPv6
        if (ip.contains("%")) {
            ip = ip.substring(0, ip.indexOf('%'));
        }

        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            if (inetAddress.isLoopbackAddress()) {
                return "127.0.0.1";
            }
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            return ip; // Retorna original se não conseguir resolver
        }
    }
}
