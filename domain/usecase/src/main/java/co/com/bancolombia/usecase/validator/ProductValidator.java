package co.com.bancolombia.usecase.validator;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.gateways.DbPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductValidator {

    private final DbPort repository;

    public Mono<Void> validateProductNameNotTaken(String name) {
        return repository.findProductByName(name)
            .flatMap(existing -> Mono.error(new BusinessException(
                ErrorCode.PRODUCT_NAME_ALREADY_EXISTS.name(),
                ErrorCode.PRODUCT_NAME_ALREADY_EXISTS.getCode()
            )))
            .then();
    }
}
