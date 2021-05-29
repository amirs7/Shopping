package xyz.softeng.shopping.shop.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import xyz.softeng.shopping.shop.domain.ShopService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/purchase")
    public void purchase(@RequestParam Long userId, @RequestParam Long productId) {
        if (!shopService.makePurchase(userId, productId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
