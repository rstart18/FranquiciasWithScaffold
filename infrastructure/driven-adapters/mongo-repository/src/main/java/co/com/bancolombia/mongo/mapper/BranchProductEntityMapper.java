package co.com.bancolombia.mongo.mapper;

import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.mongo.entity.BranchProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchProductEntityMapper {
    BranchProductEntity toEntity(BranchProduct branchProduct);
    BranchProduct toDomain(BranchProductEntity branchProductEntity);
}
