package xyz.softeng.shopping.users;


import lombok.extern.slf4j.Slf4j;
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
import xyz.softeng.shopping.common.PurchaseEvent;
import xyz.softeng.shopping.users.domain.User;
import xyz.softeng.shopping.users.domain.UserRepository;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class PurchaseEventHandlerIntegrationTest {
    private static final String PURCHASES_EXCHANGE = "test-purchases-exchange";

    @Container
    public static final RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(PURCHASES_EXCHANGE, "fanout");

    @Autowired
    private ConnectionFactory connectionFactory;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRepository userRepository;


    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("shopping.rabbit.exchange.purchases", () -> PURCHASES_EXCHANGE);
    }

    @BeforeEach
    public void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(PURCHASES_EXCHANGE);
    }

    @Test
    void testConfiguration() {
        User user = userRepository.save(new User("test", 1000));
        PurchaseEvent event = new PurchaseEvent(user.getId(), 400);
        rabbitTemplate.convertAndSend(event);
        await().atMost(5, SECONDS).until(wealthExtractor(user.getId()), equalTo(400));
    }

    private Callable<Integer> wealthExtractor(long id) {
        return () -> userRepository.findById(id).orElseThrow().getWealth();
    }
}
