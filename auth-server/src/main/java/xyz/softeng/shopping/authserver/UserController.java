package xyz.softeng.shopping.authserver;

import com.nimbusds.jose.JOSEException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyPair;
import java.util.Date;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final KeyPair keyPair;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping
    public User create(@RequestBody UserDto dto) {
        return repository.save(mapper.fromDto(dto));
    }

    @GetMapping
    public Iterable<User> list() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto credentials) throws JOSEException {
        User user = repository.findByUsername(credentials.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (passwordEncoder.matches(user.getPassword(), credentials.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return jwtService.encode(user.getUsername());
    }
}
