package parse.squarerefri.domain.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final MemberExceptionCode code;

    public MemberException(MemberExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public MemberException(String message, MemberExceptionCode code) {
        super(message);
        this.code = code;
    }
}
