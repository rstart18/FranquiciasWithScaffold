package co.com.bancolombia.mongo.mapper;

import co.com.bancolombia.model.template.Branch;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.mongo.entity.BranchEntity;
import co.com.bancolombia.mongo.entity.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchEntityMapper {
    Branch toDomain(BranchEntity branchEntity);
    BranchEntity toEntity(Branch branch);
}
