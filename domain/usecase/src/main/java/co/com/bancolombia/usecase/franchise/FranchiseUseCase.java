package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseUseCase {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Void> renameFranchise(String franchiseId, String newName);
}
