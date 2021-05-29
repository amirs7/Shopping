package xyz.softeng.shopping.users.application;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.NewUserEvent;
import xyz.softeng.shopping.users.domain.User;

@Mapper
@Component
public interface UserMapper {
    User fromDto(UserDto dto);

    NewUserEvent toEvent(User user);
}
