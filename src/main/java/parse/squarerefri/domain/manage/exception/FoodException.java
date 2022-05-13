package parse.squarerefri.domain.manage.exception;

import lombok.Getter;

@Getter
public class FoodException extends RuntimeException {
    private final FoodExceptionCode code;

    public FoodException(FoodExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public FoodException(String message, FoodExceptionCode code) {
        super(message);
        this.code = code;
    }
}
