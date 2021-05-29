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
import xyz.softeng.shopping.shop.domain.Product;
import xyz.softeng.shopping.shop.domain.ProductRepository;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
class ProductEventHandlerTest {
    private static final String PRODUCTS_EXCHANGE = "test-products-exchange";

    @Container
    public static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(PRODUCTS_EXCHANGE, "fanout");


    @Autowired
    private ConnectionFactory connectionFactory;

    @MockBean
    private ProductRepository productRepository;

    private RabbitTemplate rabbitTemplate;

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("shopping.rabbit.exchange.products", () -> PRODUCTS_EXCHANGE);
    }

    @BeforeEach
    void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.stop();
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(PRODUCTS_EXCHANGE);
    }

    @Test
    void simple() {
        Product product = new Product();
        rabbitTemplate.convertAndSend(product);
        verify(productRepository, timeout(5_000)).save(product);
    }
}
