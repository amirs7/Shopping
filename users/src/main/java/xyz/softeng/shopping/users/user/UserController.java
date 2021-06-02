package xyz.softeng.shopping.users.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class UserController {
    private final UserRepository repository;
    private final UserMapper mapper;

    @GetMapping
    public Iterable<User> list() {
        return repository.findAll();
    }

    @PostMapping
    public User create(@RequestBody UserDto dto) {
        User user = mapper.fromDto(dto);
        return repository.save(user);
    }

    @Transactional
    @DeleteMapping("/{username}")
    public void create(@PathVariable String username) {
        repository.deleteByUsername(username);
    }
}
