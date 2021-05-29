package xyz.softeng.shopping.shop.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.shop.domain.Product;
import xyz.softeng.shopping.shop.domain.ProductRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventHandler {
    private final ProductRepository productRepository;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "shop-products-in"),
                    exchange = @Exchange(value = "${shopping.rabbit.exchange.products}", declare = "false"))
    )
    public void saveNewProduct(Product product) {
        log.info("Saving Product: {}", product);
        productRepository.save(product);
    }
}
