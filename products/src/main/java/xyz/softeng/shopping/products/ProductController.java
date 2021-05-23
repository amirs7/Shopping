package xyz.softeng.shopping.products;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repository;

    private final StreamBridge streamBridge;

    @PostMapping
    public Product create(@RequestBody Product product) {
        product = repository.save(product);
        streamBridge.send("products-out-0", product);
        return product;
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        return create(product);
    }
}
