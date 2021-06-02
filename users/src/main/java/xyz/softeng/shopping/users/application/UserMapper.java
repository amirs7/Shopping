package xyz.softeng.shopping.users.application;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;
import xyz.softeng.shopping.common.events.user.UserDeletedEvent;
import xyz.softeng.shopping.users.domain.User;

@Mapper
@Component
public interface UserMapper {
    User fromDto(UserDto dto);

    UserCreatedEvent toCreateEvent(User user);

    UserDeletedEvent toDeleteEvent(User user);
}
