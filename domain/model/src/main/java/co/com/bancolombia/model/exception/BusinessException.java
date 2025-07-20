package co.com.bancolombia.model.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
}
