package xyz.softeng.shopping.authserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.authserver.user.User;
import xyz.softeng.shopping.authserver.user.UserMapper;
import xyz.softeng.shopping.authserver.user.UserRepository;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;
import xyz.softeng.shopping.common.events.user.UserDeletedEvent;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = "${auth-server.users-queue}")
public class UserEventHandler {
    private final UserRepository repository;
    private final UserMapper mapper;

    @RabbitHandler
    public void saveUser(UserCreatedEvent event) {
        log.info("Handling new user event: {}", event);
        User user = mapper.fromEvent(event);
        repository.save(user);
        log.info("User saved: {}", user.getUsername());
    }

    @RabbitHandler
    @Transactional
    public void deleteUser(UserDeletedEvent event) {
        log.info("Handling new user event: {}", event);
        repository.deleteByUsername(event.getUsername());
        log.info("User deleted: {}", event.getUsername());
    }
}
