package xyz.softeng.shopping.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShopController {
    private final PurchaseService purchaseService;

    @GetMapping("/purchase")
    @PreAuthorize("isAuthenticated()")
    public void purchase(Authentication authentication, @RequestParam Long productId) {
        String username = authentication.getName();
        if (!purchaseService.makePurchase(username, productId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
