package parse.squarerefri.domain.member.domain;

import lombok.Getter;
import lombok.Setter;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.member.model.MemberInput;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;
    private String password;
    private String memberName;
    private String phoneNumber;
    private LocalDateTime regDt;

    // 권한 USER, ADMIN
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private boolean emailAuthFlag;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    // 멤버상태 REQ, ING, STOP
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @OneToMany(mappedBy = "member")
    private List<Management> management = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Member createMember(MemberInput parameter, String encPassword, String uuid) {
        Member member = new Member();
        member.setId(parameter.getUserId());
        member.setPassword(encPassword);
        member.setMemberName(parameter.getUserName());
        member.setPhoneNumber(parameter.getPhone());
        member.setRegDt(LocalDateTime.now());
        member.setRoleType(RoleType.USER);
        member.setEmailAuthFlag(false);
        member.setEmailAuthKey(uuid);
        member.setMemberStatus(MemberStatus.REQ);

        return member;
    }

    //== 비지니스 메서드 ==//
    public void updateEmailAuth() {
        this.setMemberStatus(MemberStatus.ING);
        this.setEmailAuthFlag(true);
        this.setEmailAuthDt(LocalDateTime.now());
    }

    public void updatePreResetPassword(String uuid) {
        this.setResetPasswordKey(uuid);
        this.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
    }

}
