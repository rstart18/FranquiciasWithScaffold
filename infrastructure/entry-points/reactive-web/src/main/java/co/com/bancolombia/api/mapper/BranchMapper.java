package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.branch.BranchRequest;
import co.com.bancolombia.api.dto.branch.BranchResponse;
import co.com.bancolombia.model.template.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch toDomain(BranchRequest request);
    BranchResponse toResponseDto(Branch branch);
}
