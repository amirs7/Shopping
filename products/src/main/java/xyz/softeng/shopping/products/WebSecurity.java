package xyz.softeng.shopping.products;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.products.product.ProductRepository;

@Component
@RequiredArgsConstructor
public class WebSecurity {
    private final ProductRepository repository;

    public boolean owns(String username, Long productId) {
        return repository.findById(productId).orElseThrow().getVendorUsername().equals(username);
    }
}
