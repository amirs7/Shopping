package xyz.softeng.shopping.products.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.softeng.shopping.products.domain.Product;
import xyz.softeng.shopping.products.domain.ProductRepository;

import java.security.Principal;

@RestController
@RequestMapping
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProductController {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @GetMapping
    public Iterable<Product> list() {
        return repository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_VENDOR')")
    public Product create(@RequestBody ProductDto dto, Principal principal) {
        Product product = mapper.fromDto(dto);
        product.setVendorUsername(principal.getName());
        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@webSecurity.owns(authentication.name, #id)")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}