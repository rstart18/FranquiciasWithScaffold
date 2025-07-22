package co.com.bancolombia.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FRANCHISE_NOT_FOUND("FRANCHISE_NOT_FOUND", "Franchise not found"),
    FRANCHISE_ALREADY_EXISTS("FRANCHISE_ALREADY_EXISTS", "Franchise not found"),
    BRANCH_NOT_FOUND("BRANCH_NOT_FOUND", "Branch not found"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Product not found"),
    PRODUCT_NAME_NOT_FOUND("PRODUCT_NAME_NOT_FOUND", "Product name not found"),
    PRODUCT_NAME_ALREADY_EXISTS("PRODUCT_NAME_ALREADY_EXISTS", "Product name already exists"),
    BRANCH_PRODUCT_NOT_FOUND("BRANCH_PRODUCT_NOT_FOUND", "Product not found");

    private final String code;
    private final String message;
}
