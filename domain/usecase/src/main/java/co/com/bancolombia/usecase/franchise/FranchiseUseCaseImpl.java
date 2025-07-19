package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.exception.NotFoundException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCaseImpl implements FranchiseUseCase {
    private final FranchiseRepository repository;

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return repository.save(franchise);
    }

    @Override
    public Mono<Void> renameFranchise(String franchiseId, String newName) {
        return repository.findById(franchiseId)
            .switchIfEmpty(Mono.error(new NotFoundException(
                ErrorCode.FRANCHISE_NOT_FOUND.getMessage(), ErrorCode.FRANCHISE_NOT_FOUND.getCode())))
            .flatMap(franchise -> {
                franchise.setName(newName);
                return repository.save(franchise).then();
            });
    }

}
