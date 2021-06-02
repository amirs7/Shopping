package xyz.softeng.shopping.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.users.user.UserRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PurchaseEventHandler {
    private final UserRepository userRepository;

    @RabbitListener(queues = "${user-service.purchases-queue}")
    public void updateUserWealth(PurchaseEvent event) {
        log.info("Handling purchase event: {}", event);
        userRepository.findById(event.getUserId())
                .ifPresent(user -> {
                    user.decreaseWealth(event.getCost());
                    userRepository.save(user);
                });
    }

    @Bean
    public Queue purchaseQueue(UserServiceProperties properties) {
        return QueueBuilder.durable(properties.getPurchaseQueue()).build();
    }

    @Bean
    public FanoutExchange purchaseExchange(UserServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getPurchaseExchange())
                .suppressDeclaration()
                .build();
    }

    @Bean
    public Binding purchasesBinding(Queue purchaseQueue, FanoutExchange purchaseExchange) {
        return BindingBuilder.bind(purchaseQueue).to(purchaseExchange);
    }
}
