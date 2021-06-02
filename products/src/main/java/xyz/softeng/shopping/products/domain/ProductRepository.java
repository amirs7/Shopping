package xyz.softeng.shopping.products.domain;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findAllByVendorUsername(String vendor);
}
