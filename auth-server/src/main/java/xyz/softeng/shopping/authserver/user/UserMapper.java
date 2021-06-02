package xyz.softeng.shopping.authserver.user;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.softeng.shopping.common.NewUserEvent;

@Mapper
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract User fromDto(UserDto dto);

    public abstract NewUserEvent toEvent(User user);

    @AfterMapping
    User encodePassword(@MappingTarget User user, UserDto dto) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}