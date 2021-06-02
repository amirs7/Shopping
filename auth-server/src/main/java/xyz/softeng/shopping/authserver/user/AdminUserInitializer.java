package xyz.softeng.shopping.authserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.authserver.AuthServerProperties;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer {
    private final UserRepository userRepository;
    private final AuthServerProperties properties;

    public void init() {
        if (userRepository.findByUsername(properties.getAdminUser()).isPresent())
            return;
        User user = new User();
        user.setUsername(properties.getAdminUser());
        user.setUsername(properties.getAdminPass());
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);
    }
}
