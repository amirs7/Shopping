package xyz.softeng.shopping.products.product;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.product.ProductCreatedEvent;
import xyz.softeng.shopping.common.events.product.ProductDeletedEvent;

@Mapper
@Component
public interface ProductMapper {
    Product fromDto(ProductDto dto);

    ProductCreatedEvent toCreateEvent(Product product);

    ProductDeletedEvent toDeleteEvent(Product product);
}
