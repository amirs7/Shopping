package xyz.softeng.shopping.products.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.softeng.shopping.products.domain.Product;
import xyz.softeng.shopping.products.domain.ProductRepository;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    public Product create(@RequestBody ProductDto dto) {
        Product product = mapper.fromDto(dto);
        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@webSecurity.owns(principal.username, #id)")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
