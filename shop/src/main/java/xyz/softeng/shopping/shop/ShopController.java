package xyz.softeng.shopping.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShopService shopService;
    private final StreamBridge streamBridge;

    @GetMapping
    public void buyProduct(@RequestParam Long userId, @RequestParam Long productId) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        if (!shopService.canBuy(user, product))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        streamBridge.send("shop-out-0", new BuyEvent(userId, productId));
    }
}
