package xyz.softeng.shopping.users.user;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.user.UserEvent;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Slf4j
@Component
@NoArgsConstructor
public class UserUpdateListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserMapper mapper;

    @PostPersist
    @PostUpdate
    public void onUserCreated(User user) {
        UserEvent event = mapper.toCreateEvent(user);
        rabbitTemplate.convertAndSend(event);
        log.info("NewUser event sent: {}", event);
    }

    @PostRemove
    public void onUserRemoved(User user) {
        UserEvent event = mapper.toDeleteEvent(user);
        rabbitTemplate.convertAndSend(event);
        log.info("DeleteUser event sent: {}", event);
    }
}
