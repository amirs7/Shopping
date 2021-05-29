package xyz.softeng.shopping.products;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @PostMapping
    public Product create(@RequestBody ProductDto dto) {
        Product product = mapper.fromDto(dto);
        return repository.save(product);
    }
}
