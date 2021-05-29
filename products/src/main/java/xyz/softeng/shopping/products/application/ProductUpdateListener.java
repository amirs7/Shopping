package xyz.softeng.shopping.products.application;

import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.products.domain.Product;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
@NoArgsConstructor
public class ProductUpdateListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductMapper mapper;

    @PostUpdate
    @PostPersist
    public void onProductUpdate(Product product) {
        rabbitTemplate.convertAndSend(mapper.toEvent(product));
    }
}
