package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import reactor.core.publisher.Mono;

public interface BranchUseCase {
    Mono<Branch> createBranch(Branch branch);
    Mono<Void> renameBranch(String id, String newName);
}
