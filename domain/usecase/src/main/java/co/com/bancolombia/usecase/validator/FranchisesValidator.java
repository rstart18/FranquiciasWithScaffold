package co.com.bancolombia.usecase.validator;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.gateways.DbPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchisesValidator {

    private final DbPort repository;

    public Mono<Void> validateUniqueFranchise(String nit, String name) {
        return repository.findFranchiseByNitOrName(nit, name)
            .flatMap(existing -> Mono.error(new BusinessException(
                ErrorCode.FRANCHISE_ALREADY_EXISTS.name(),
                ErrorCode.FRANCHISE_ALREADY_EXISTS.getCode()
            )))
            .then();
    }

    public Mono<Void> validateFranchiseNameNotTaken(String newName) {
        return repository.findFranchiseByName(newName)
            .flatMap(existing -> Mono.error(new BusinessException(
                ErrorCode.FRANCHISE_ALREADY_EXISTS.name(),
                ErrorCode.FRANCHISE_ALREADY_EXISTS.getCode()
            )))
            .then();
    }


}
