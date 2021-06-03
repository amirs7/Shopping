package xyz.softeng.shopping.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import xyz.softeng.shopping.shop.user.User;
import xyz.softeng.shopping.shop.user.UserRepository;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
class UserEventHandlerIT {
    private static final String USERS_EXCHANGE = "test-users-exchange";

    @Container
    public static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(USERS_EXCHANGE, "fanout");

    @Autowired
    private ConnectionFactory connectionFactory;

    @MockBean
    private UserRepository userRepository;

    private RabbitTemplate rabbitTemplate;

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("shopping.rabbit.exchange.users", () -> USERS_EXCHANGE);
    }

    @BeforeEach
    void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.stop();
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(USERS_EXCHANGE);
    }

    @Test
    void simple() {
        User user = new User();
        rabbitTemplate.convertAndSend(user);
        verify(userRepository, timeout(5_000)).save(user);
    }
}
