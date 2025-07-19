package co.com.bancolombia.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FRANCHISE_NOT_FOUND("FRANCHISE_NOT_FOUND", "Franchise not found"),
    BRANCH_NOT_FOUND("BRANCH_NOT_FOUND", "Branch not found"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Product not found"),
    BRANCH_PRODUCT_NOT_FOUND("BRANCH_PRODUCT_NOT_FOUND", "Product not found");

    private final String code;
    private final String message;
}
