package xyz.softeng.shopping.products.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.products.domain.ProductRepository;

@Component
@RequiredArgsConstructor
public class WebSecurity {
    private final ProductRepository repository;

    public boolean owns(String username, Long productId) {
        return repository.findById(productId).orElseThrow().getVendorUsername().equals(username);
    }
}
