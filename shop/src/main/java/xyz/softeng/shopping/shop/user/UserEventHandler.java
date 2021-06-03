package xyz.softeng.shopping.shop.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;
import xyz.softeng.shopping.common.events.user.UserDeletedEvent;
import xyz.softeng.shopping.shop.ShopServiceProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserEventHandler {
    private final UserRepository repository;
    private final UserMapper mapper;

    @RabbitListener(queues = "${shop-service.user-queue}")
    public void saveUser(UserCreatedEvent event) {
        log.info("Saving new user: {}", event);
        User user = mapper.fromEvent(event);
        repository.save(user);
    }

    @RabbitListener(queues = "${shop-service.user-queue}")
    public void deleteUser(UserDeletedEvent event) {
        log.info("Deleting user: {}", event);
        repository.deleteById(event.getUsername());
    }

    @Bean
    public FanoutExchange userExchange(ShopServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getUserExchange()).suppressDeclaration().build();
    }

    @Bean
    public Queue userQueue(ShopServiceProperties properties) {
        return QueueBuilder.durable(properties.getUserQueue()).build();
    }

    @Bean
    public Binding userBinding(FanoutExchange userExchange, Queue userQueue) {
        return BindingBuilder.bind(userQueue).to(userExchange);
    }
}
