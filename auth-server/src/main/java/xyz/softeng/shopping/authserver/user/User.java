package xyz.softeng.shopping.authserver.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.authserver.AuthServerProperties;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;
import xyz.softeng.shopping.common.events.user.UserDeletedEvent;

import javax.persistence.*;
import javax.transaction.Transactional;

@Data
@Entity
@Table(name = "auth_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String username;

    private String password;

    private Role role;

    public enum Role {
        ADMIN,
        VENDOR,
        CUSTOMER
    }

    @Slf4j
    @Configuration
    @RequiredArgsConstructor
    @RabbitListener(queues = "${auth-server.user-queue}")
    public static class UserEventHandler {
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

        @Bean
        public FanoutExchange userExchange(AuthServerProperties properties) {
            return ExchangeBuilder.fanoutExchange(properties.getUserExchange()).suppressDeclaration().build();
        }

        @Bean
        public Queue userQueue(AuthServerProperties properties) {
            return QueueBuilder.durable(properties.getUserQueue()).build();
        }

        @Bean
        public Binding userBinding(Queue usersQueue, FanoutExchange usersExchange) {
            return BindingBuilder.bind(usersQueue).to(usersExchange);
        }
    }
}
