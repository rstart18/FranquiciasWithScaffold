package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.branchProduct.BranchProductRequest;
import co.com.bancolombia.api.dto.branchProduct.BranchProductResponse;
import co.com.bancolombia.model.template.BranchProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchProductMapper {
    BranchProduct toDomain(BranchProductRequest request);
    BranchProductResponse toResponseDto(BranchProduct branchProduct);
}
