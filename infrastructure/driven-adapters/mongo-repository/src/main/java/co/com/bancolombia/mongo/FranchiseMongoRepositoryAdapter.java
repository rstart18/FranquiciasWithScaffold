package co.com.bancolombia.mongo;

import co.com.bancolombia.model.template.Branch;
import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.model.gateways.DbPort;
import co.com.bancolombia.model.template.Product;
import co.com.bancolombia.mongo.mapper.BranchEntityMapper;
import co.com.bancolombia.mongo.mapper.BranchProductEntityMapper;
import co.com.bancolombia.mongo.mapper.FranchiseEntityMapper;
import co.com.bancolombia.mongo.mapper.ProductEntityMapper;
import co.com.bancolombia.mongo.repository.BranchMongoDBRepository;
import co.com.bancolombia.mongo.repository.BranchProductMongoDBRepository;
import co.com.bancolombia.mongo.repository.FranchiseMongoDBRepository;
import co.com.bancolombia.mongo.repository.ProductMongoDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranchiseMongoRepositoryAdapter implements DbPort {
    private final FranchiseMongoDBRepository franchiseMongoDBRepository;
    private final BranchMongoDBRepository branchMongoDBRepository;
    private final FranchiseEntityMapper franchiseEntityMapper;
    private final BranchEntityMapper branchEntityMapper;
    private final ProductMongoDBRepository productMongoDBRepository;
    private final ProductEntityMapper productEntityMapper;
    private final BranchProductMongoDBRepository branchProductMongoDBRepository;
    private final BranchProductEntityMapper branchProductEntityMapper;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseMongoDBRepository.save(franchiseEntityMapper.toEntity(franchise))
            .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findFranchiseById(String id) {
        return franchiseMongoDBRepository.findById(id)
            .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findFranchiseByNitOrName(String nit, String name) {
        return franchiseMongoDBRepository.findByNitOrName(nit, name)
            .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findFranchiseByName(String name) {
        return franchiseMongoDBRepository.findByName(name)
            .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<String> getProductNameById(String productId) {
        return productMongoDBRepository.getNameById(productId);
    }

    @Override
    public Flux<Branch> findAllBranchByFranchiseId(String franchiseId) {
        return branchMongoDBRepository.findByFranchiseId(franchiseId)
            .map(branchEntityMapper::toDomain);
    }

    @Override
    public Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct) {
        return branchProductMongoDBRepository.save(branchProductEntityMapper.toEntity(branchProduct))
            .map(branchProductEntityMapper::toDomain);
    }

    @Override
    public Mono<BranchProduct> findBranchProductByBranchIdAndProductId(String branchId, String productId) {
        return branchProductMongoDBRepository.findByBranchIdAndProductId(branchId, productId)
            .map(branchProductEntityMapper::toDomain);
    }

    @Override
    public Flux<BranchProduct> findAllBranchProductByBranchId(String branchId) {
        return branchProductMongoDBRepository.findAllByBranchId(branchId)
            .map(branchProductEntityMapper::toDomain);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productMongoDBRepository.save(productEntityMapper.toEntity(product))
            .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Product> findProductById(String id) {
        return productMongoDBRepository.findById(id)
            .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Product> findProductByName(String name) {
        return productMongoDBRepository.findByName(name)
            .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return branchMongoDBRepository.save(branchEntityMapper.toEntity(branch))
            .map(branchEntityMapper::toDomain);
    }
}
