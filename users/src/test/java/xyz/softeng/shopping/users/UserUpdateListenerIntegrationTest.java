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
import org.testcontainers.junit.jupiter.Testcontainers;
import xyz.softeng.shopping.common.NewUserEvent;
import xyz.softeng.shopping.users.domain.User;
import xyz.softeng.shopping.users.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class UserUpdateListenerIntegrationTest {
    static final String USERS_EXCHANGE = "test-users-exchange";

//    @Container
//    static final RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
//            .withExchange(USERS_EXCHANGE, "fanout");

//    @DynamicPropertySource
//    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.rabbitmq.host", rabbit::getHost);
//        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
//        registry.add("shopping.rabbit.exchange.purchases", () -> USERS_EXCHANGE);
//    }

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
        rabbitTemplate.setExchange(USERS_EXCHANGE);
        FanoutExchange usersExchange = ExchangeBuilder.fanoutExchange(USERS_EXCHANGE).suppressDeclaration().build();
        usersQueue = QueueBuilder.nonDurable().build();
        rabbitAdmin.declareQueue(usersQueue);
        Binding usersQueueBinding = BindingBuilder.bind(usersQueue).to(usersExchange);
        rabbitAdmin.declareBinding(usersQueueBinding);
    }

    @Test
    void testUserEventIsSent() throws InterruptedException {
        User user = new User("test", 1000);
        userRepository.save(user);
        NewUserEvent event = (NewUserEvent) rabbitTemplate.receiveAndConvert(usersQueue.getName(), 5_000);
        assertThat(event).usingRecursiveComparison().isEqualTo(user);
    }
}
