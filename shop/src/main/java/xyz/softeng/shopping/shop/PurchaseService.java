package xyz.softeng.shopping.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.shop.product.Product;
import xyz.softeng.shopping.shop.product.ProductRepository;
import xyz.softeng.shopping.shop.user.User;
import xyz.softeng.shopping.shop.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseService {
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public boolean makePurchase(String username, Long productId) {
        User user = userRepository.findById(username).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        if (!user.canPurchase(product))
            return false;

        PurchaseEvent purchaseEvent = new PurchaseEvent(username, product.getId(), product.getPrice());
        rabbitTemplate.convertAndSend(purchaseEvent);
        log.info("Purchase event sent: {}", purchaseEvent);
        return true;
    }
}
