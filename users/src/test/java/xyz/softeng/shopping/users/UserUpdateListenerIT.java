package xyz.softeng.shopping.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import xyz.softeng.shopping.common.events.user.UserCreatedEvent;
import xyz.softeng.shopping.users.user.User;
import xyz.softeng.shopping.users.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class UserUpdateListenerIT {
    static final String USER_EXCHANGE = "test-user-exchange";

    @Container
    static final RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management");

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("eshop.exchange.user", () -> USER_EXCHANGE);
    }

    @Autowired
    ConnectionFactory connectionFactory;

    RabbitTemplate rabbitTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RabbitAdmin rabbitAdmin;

    Queue usersQueue;

    @BeforeEach
    void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(USER_EXCHANGE);
        FanoutExchange usersExchange = ExchangeBuilder.fanoutExchange(USER_EXCHANGE).suppressDeclaration().build();
        usersQueue = QueueBuilder.nonDurable().build();
        rabbitAdmin.declareQueue(usersQueue);
        Binding usersQueueBinding = BindingBuilder.bind(usersQueue).to(usersExchange);
        rabbitAdmin.declareBinding(usersQueueBinding);
    }

    @Test
    void testUserEventIsSent() {
        User user = new User("test", 1000);
        userRepository.save(user);
        UserCreatedEvent event = (UserCreatedEvent) rabbitTemplate.receiveAndConvert(usersQueue.getName(), 5_000);
        assertThat(event).usingRecursiveComparison().isEqualTo(user);
    }
}
