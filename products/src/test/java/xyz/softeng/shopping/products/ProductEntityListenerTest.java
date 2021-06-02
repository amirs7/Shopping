package xyz.softeng.shopping.products;

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
import xyz.softeng.shopping.common.events.product.ProductCreatedEvent;
import xyz.softeng.shopping.products.product.Product;
import xyz.softeng.shopping.products.product.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ProductEntityListenerTest {

    static final String PRODUCTS_EXCHANGE = "test-products-exchange";

    @Container
    static final RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExchange(PRODUCTS_EXCHANGE, "fanout");

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("shopping.rabbit.exchange.products", () -> PRODUCTS_EXCHANGE);
    }

    @Autowired
    ConnectionFactory connectionFactory;

    RabbitTemplate rabbitTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RabbitAdmin rabbitAdmin;

    Queue productsQueue;

    @BeforeEach
    void setup() {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(PRODUCTS_EXCHANGE);
        FanoutExchange productsExchange = ExchangeBuilder.fanoutExchange(PRODUCTS_EXCHANGE).suppressDeclaration().build();
        productsQueue = QueueBuilder.nonDurable().build();
        rabbitAdmin.declareQueue(productsQueue);
        Binding usersQueueBinding = BindingBuilder.bind(productsQueue).to(productsExchange);
        rabbitAdmin.declareBinding(usersQueueBinding);
    }

    @Test
    void testUserEventIsSent() {
        Product product = new Product("pc", 1000);
        productRepository.save(product);
        ProductCreatedEvent event = (ProductCreatedEvent) rabbitTemplate.receiveAndConvert(productsQueue.getName(), 5_000);
        assertThat(event).usingRecursiveComparison().isEqualTo(product);
    }
}
