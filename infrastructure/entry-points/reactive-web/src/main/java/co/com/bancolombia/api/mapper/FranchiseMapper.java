package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.franchise.FranchiseRequest;
import co.com.bancolombia.api.dto.franchise.FranchiseResponse;
import co.com.bancolombia.model.template.Franchise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {
    Franchise toDomain(FranchiseRequest request);
    FranchiseResponse toResponseDto(Franchise franchise);
}
