package co.com.bancolombia.mongo.mapper;

import co.com.bancolombia.model.template.Product;
import co.com.bancolombia.mongo.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    ProductEntity toEntity(Product product);
    Product toDomain(ProductEntity productEntity);
}
