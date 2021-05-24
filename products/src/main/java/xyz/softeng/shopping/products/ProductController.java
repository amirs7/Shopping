package xyz.softeng.shopping.products;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
        Product product = repository.findById(id).orElseThrow();
        product = mapper.update(product, dto);
        return repository.save(product);
    }
}
