package co.com.bancolombia.mongo.mapper;

import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.mongo.entity.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseEntityMapper {
    Franchise toDomain(FranchiseEntity franchiseEntity);
    FranchiseEntity toEntity(Franchise franchise);
}
