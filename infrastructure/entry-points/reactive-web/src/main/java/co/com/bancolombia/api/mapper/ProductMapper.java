package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.product.ProductRequest;
import co.com.bancolombia.api.dto.product.ProductResponse;
import co.com.bancolombia.model.template.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomain(ProductRequest request);
    ProductResponse toResponseDto(Product product);
}
