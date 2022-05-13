package parse.squarerefri.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode {
    MEMBER_NOT_EMAIL_AUTH("정지된 회원입니다."),
    MEMBER_STOP_USER("이메일 활성화 이후에 로그인 해주세요"),
    MEMBER_USERNAME_NOT_FOUND("회원 정보가 존재하지 않습니다."),
    NOT_VALID_DATE("유효한 날짜가 아닙니다.")
    ;

    private final String message;
}
