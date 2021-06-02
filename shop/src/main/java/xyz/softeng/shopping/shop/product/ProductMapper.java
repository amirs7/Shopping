package xyz.softeng.shopping.shop.product;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.common.events.product.ProductCreatedEvent;
import xyz.softeng.shopping.shop.product.Product;

@Mapper
@Component
public interface ProductMapper {
    Product fromEvent(ProductCreatedEvent event);
}
