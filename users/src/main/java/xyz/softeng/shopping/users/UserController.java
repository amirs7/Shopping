package xyz.softeng.shopping.users;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    private final StreamBridge streamBridge;

    @PostMapping
    public User create(@RequestBody User user) {
        user = repository.save(user);
        streamBridge.send("users-out-0", user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return create(user);
    }
}
