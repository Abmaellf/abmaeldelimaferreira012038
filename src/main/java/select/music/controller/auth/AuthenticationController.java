package select.music.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import select.music.dto.auth.AuthenticationRequestDTO;
import select.music.dto.auth.AuthenticationResponseDTO;
import select.music.dto.auth.LoginResponseDTO;
import select.music.service.auth.AuthenticationCookieService;
import select.music.service.auth.AuthenticationService;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthenticationController {


    private AuthenticationService authenticationService;
    private AuthenticationCookieService authenticationCookieService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO data,
                                                           HttpServletRequest req, HttpServletResponse res){

        //                                HttpRequest request, HttpResponse response
      //  String ipAddress = clientIpAddressService.getClientIpAddress(request);

        AuthenticationResponseDTO response = authenticationService.authenticate(data);
        String meuToken = response.token();
        String refreshToken = response.refreshToken();

        if (meuToken == null) {
            return null;
        }

        authenticationCookieService.addAuthCookie(res, meuToken);
        authenticationCookieService.addRefreshTokenCookie(res, refreshToken);

        return ResponseEntity.ok( response);
    }
}
