package xyz.softeng.shopping.shop.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.shop.domain.User;
import xyz.softeng.shopping.shop.domain.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventHandler {
    private final UserRepository userRepository;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue("shop-users-in"),
                    exchange = @Exchange(value = "${shopping.rabbit.exchange.users}", declare = "false"))
    )
    public void saveNewUser(User user) {
        log.info("Saving new user: {}", user);
        userRepository.save(user);
    }
}
