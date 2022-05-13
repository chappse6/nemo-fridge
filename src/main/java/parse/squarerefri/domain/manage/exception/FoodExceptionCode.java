package parse.squarerefri.domain.manage.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodExceptionCode {
    NOT_FOUND_FOOD("식품 정보가 존재하지 않습니다.");

    private final String message;
}
