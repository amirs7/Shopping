package xyz.softeng.shopping.products;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import xyz.softeng.shopping.products.ProductDto;

@Mapper
public interface ProductMapper {
    Product fromDto(ProductDto dto);

    Product update(@MappingTarget Product product, ProductDto dto);
}
