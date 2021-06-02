package xyz.softeng.shopping.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.users.domain.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseEventHandler {
    private final UserRepository userRepository;

    @RabbitListener(queues = "${shopping.users.purchases-queue}")
    public void updateUserWealth(PurchaseEvent event) {
        log.info("Handling purchase event: {}", event);
        userRepository.findById(event.getUserId())
                .ifPresent(user -> {
                    user.decreaseWealth(event.getCost());
                    userRepository.save(user);
                });
    }
}
