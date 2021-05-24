package xyz.softeng.shopping.users;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    private final UserMapper mapper;

    private final StreamBridge streamBridge;

    @PostMapping
    public User create(@RequestBody UserDto dto) {
        User user = mapper.fromDto(dto);
        return repository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserDto dto) {
        User user = repository.findById(id).orElseThrow();
        user = mapper.update(user, dto);
        return repository.save(user);
    }
}
