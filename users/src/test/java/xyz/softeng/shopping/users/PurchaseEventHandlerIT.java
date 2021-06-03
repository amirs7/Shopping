package xyz.softeng.shopping.users;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.users.user.User;
import xyz.softeng.shopping.users.user.UserRepository;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class PurchaseEventHandlerIT {
    private static final String PURCHASE_EXCHANGE = "test-purchases-exchange";

    @Container
    public static final RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(PURCHASE_EXCHANGE, "fanout");

    @Autowired
    private ConnectionFactory connectionFactory;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRepository userRepository;


    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("eshop.exchange.purchase", () -> PURCHASE_EXCHANGE);
    }

    @BeforeEach
    public void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(PURCHASE_EXCHANGE);
    }

    @Test
    void testConfiguration() {
        User user = userRepository.save(new User("test", 1000));
        PurchaseEvent event = new PurchaseEvent(user.getUsername(), null, 400);
        rabbitTemplate.convertAndSend(event);
        await().atMost(5, SECONDS).until(wealthExtractor(user.getId()), equalTo(500));
    }

    private Callable<Integer> wealthExtractor(long id) {
        return () -> userRepository.findById(id).orElseThrow().getWealth();
    }
}
