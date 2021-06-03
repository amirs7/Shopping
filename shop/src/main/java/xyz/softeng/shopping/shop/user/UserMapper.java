package xyz.softeng.shopping.shop.user;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;

@Mapper
@Component
public interface UserMapper {
    User fromEvent(UserCreatedEvent event);
}
