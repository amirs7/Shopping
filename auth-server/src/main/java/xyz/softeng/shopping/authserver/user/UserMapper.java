package xyz.softeng.shopping.authserver.user;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;

@Mapper
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract User fromEvent(UserCreatedEvent event);

    @AfterMapping
    User encodePassword(@MappingTarget User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
