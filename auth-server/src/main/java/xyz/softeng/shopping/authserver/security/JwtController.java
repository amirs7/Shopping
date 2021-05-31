package xyz.softeng.shopping.authserver.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import xyz.softeng.shopping.authserver.user.User;
import xyz.softeng.shopping.authserver.user.UserDto;
import xyz.softeng.shopping.authserver.user.UserRepository;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JWKSet jwkSet;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwkSet() {
        return this.jwkSet.toJSONObject();
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto credentials) throws JOSEException {
        User user = userRepository.findByUsername(credentials.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (passwordEncoder.matches(user.getPassword(), credentials.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return jwtService.encode(user.getUsername());
    }
}