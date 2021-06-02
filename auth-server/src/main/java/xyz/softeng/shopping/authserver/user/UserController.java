package xyz.softeng.shopping.authserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;
    private final UserMapper mapper;

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
