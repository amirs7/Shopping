package xyz.softeng.shopping.rating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.rating.rating.PurchaseRating;
import xyz.softeng.shopping.rating.rating.RatingRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class PurchaseEventHandlerIT {
    private static final String PURCHASE_EXCHANGE = "test-purchase-exchange";

    @Container
    public static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(PURCHASE_EXCHANGE, "fanout");


    @Autowired
    private ConnectionFactory connectionFactory;

    @MockBean
    private RatingRepository ratingRepository;

    private RabbitTemplate rabbitTemplate;

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("eshop.exchange.purchase", () -> PURCHASE_EXCHANGE);
    }

    @BeforeEach
    void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.stop();
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(PURCHASE_EXCHANGE);
    }

    @Test
    void testHandlingPurchaseEvent() {
        PurchaseEvent event = new PurchaseEvent();
        event.setUsername("amir");
        event.setProductId(10L);

        rabbitTemplate.convertAndSend(event);

        var captor = ArgumentCaptor.forClass(PurchaseRating.class);
        verify(ratingRepository, timeout(5_000)).save(captor.capture());
        PurchaseRating rating = captor.getValue();
        assertThat(rating.getUsername()).isEqualTo("amir");
        assertThat(rating.getProductId()).isEqualTo(10L);
    }
}
