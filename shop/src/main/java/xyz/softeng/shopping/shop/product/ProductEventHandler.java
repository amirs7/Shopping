package xyz.softeng.shopping.shop.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.common.events.product.ProductCreatedEvent;
import xyz.softeng.shopping.common.events.product.ProductDeletedEvent;
import xyz.softeng.shopping.shop.ShopServiceProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
@RabbitListener(queues = "${shop-service.product-queue}")
public class ProductEventHandler {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @RabbitHandler
    public void saveProduct(ProductCreatedEvent event) {
        log.info("Saving Product: {}", event);
        Product product = mapper.fromEvent(event);
        repository.save(product);
    }

    @RabbitHandler
    public void deleteProduct(ProductDeletedEvent event) {
        log.info("Deleting product: {}", event);
        repository.deleteById(event.getId());
    }

    @Bean
    public FanoutExchange productExchange(ShopServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getProductExchange())
                .suppressDeclaration()
                .build();
    }

    @Bean
    public Queue productQueue(ShopServiceProperties properties) {
        return QueueBuilder.durable(properties.getProductQueue()).build();
    }

    @Bean
    public Binding productBinding(FanoutExchange productExchange, Queue productQueue) {
        return BindingBuilder.bind(productQueue).to(productExchange);
    }
}
