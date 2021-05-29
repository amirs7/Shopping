package xyz.softeng.shopping.products.application;

import org.mapstruct.Mapper;
import xyz.softeng.shopping.common.NewProductEvent;
import xyz.softeng.shopping.products.domain.Product;

@Mapper
public interface ProductMapper {
    Product fromDto(ProductDto dto);

    NewProductEvent toEvent(Product product);
}
