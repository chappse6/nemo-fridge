package parse.squarerefri.domain.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.model.MemberInput;
import parse.squarerefri.domain.member.model.ResetPasswordInput;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화 함.
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 비밀번호 초기화
     */
    boolean resetPassword(String uuid, String password);

    /**
     * 입력받은 UUID 확인
     */
    boolean checkResetPassword(String uuid);

    /**
     * 회원 목록 리턴(관리자에서만 사용 가능)
     */
    //List<MemberDto> list(MemberParam parameter);

    /**
     * 회원 상세 정보
     */
    //MemberDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);

    /**
     * 회원 조회
     */
    Member searchMember(String userId);
}
