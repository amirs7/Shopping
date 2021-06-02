package xyz.softeng.shopping.products.application;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.product.ProductCreatedEvent;
import xyz.softeng.shopping.common.events.product.ProductDeletedEvent;
import xyz.softeng.shopping.products.domain.Product;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

@Slf4j
@Component
@NoArgsConstructor
public class ProductEntityListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductMapper mapper;

    @PostPersist
    public void onCreated(Product product) {
        ProductCreatedEvent event = mapper.toCreateEvent(product);
        rabbitTemplate.convertAndSend(event);
        log.info("Create event sent: {}", event);
    }

    @PostRemove
    public void onRemoved(Product product) {
        ProductDeletedEvent event = mapper.toDeleteEvent(product);
        rabbitTemplate.convertAndSend(event);
        log.info("Delete event sent: {}", event);
    }
}
