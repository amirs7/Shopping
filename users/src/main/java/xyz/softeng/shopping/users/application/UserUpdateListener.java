package xyz.softeng.shopping.users.application;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.users.domain.User;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
@Component
@NoArgsConstructor
public class UserUpdateListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserMapper mapper;

    @PostUpdate
    @PostPersist
    public void onUserUpdate(User user) {
        rabbitTemplate.convertAndSend(mapper.toEvent(user));
        log.info("User event sent");
    }
}
