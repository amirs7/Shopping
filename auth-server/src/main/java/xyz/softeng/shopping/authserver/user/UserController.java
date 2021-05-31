package xyz.softeng.shopping.authserver.user;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.softeng.shopping.authserver.security.JwtService;

import java.security.KeyPair;


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

}
