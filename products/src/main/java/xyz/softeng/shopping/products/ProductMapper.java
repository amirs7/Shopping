package xyz.softeng.shopping.products;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {
    Product fromDto(ProductDto dto);

    Product update(@MappingTarget Product product, ProductDto dto);
}
