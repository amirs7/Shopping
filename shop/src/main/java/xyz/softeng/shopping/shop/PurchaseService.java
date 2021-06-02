package xyz.softeng.shopping.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.PurchaseEvent;
import xyz.softeng.shopping.shop.product.Product;
import xyz.softeng.shopping.shop.product.ProductRepository;
import xyz.softeng.shopping.shop.user.User;
import xyz.softeng.shopping.shop.user.UserRepository;

@Component
@RequiredArgsConstructor
public class PurchaseService {
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public boolean makePurchase(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        if (!user.canPurchase(product))
            return false;

        PurchaseEvent purchaseEvent = new PurchaseEvent(user.getId(), product.getId(), product.getPrice());
        rabbitTemplate.convertAndSend(purchaseEvent);
        return true;
    }
}
