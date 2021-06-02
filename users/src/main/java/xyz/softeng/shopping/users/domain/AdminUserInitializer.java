package xyz.softeng.shopping.users.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.UserRole;
import xyz.softeng.shopping.users.UserServiceConfigurationProperties;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer {
    private final UserRepository userRepository;
    private final UserServiceConfigurationProperties properties;

    @PostConstruct
    public void init() {
        if (userRepository.existsByUsername(properties.getAdminUser()))
            return;
        User user = new User();
        user.setUsername(properties.getAdminUser());
        user.setPassword(properties.getAdminPass());
        user.setRole(UserRole.ADMIN);
        userRepository.save(user);
    }
}
