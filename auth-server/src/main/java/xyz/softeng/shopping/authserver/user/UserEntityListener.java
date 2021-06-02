package xyz.softeng.shopping.authserver.user;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
@Component
@NoArgsConstructor
public class UserEntityListener {
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
