package xyz.softeng.shopping.products;

import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
@NoArgsConstructor
public class ProductUpdateListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostUpdate
    @PostPersist
    public void onProductUpdate(Product product) {
        rabbitTemplate.convertAndSend(product);
    }
}
