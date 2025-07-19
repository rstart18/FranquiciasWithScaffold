package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCaseImpl implements BranchUseCase {
    private final BranchRepository branchRepository;

    @Override
    public Mono<Branch> createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Mono<Void> renameBranch(String id, String newName) {
        return branchRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException(
                ErrorCode.BRANCH_NOT_FOUND.getMessage(), ErrorCode.BRANCH_NOT_FOUND.getCode())))
            .flatMap(branch -> {
                branch.setName(newName);
                return branchRepository.save(branch);
            })
            .then();
    }

}
