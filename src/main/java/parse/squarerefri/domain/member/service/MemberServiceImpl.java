package parse.squarerefri.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parse.squarerefri.domain.components.MailComponents;
import parse.squarerefri.domain.mail.entity.MailTemplateType;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.domain.MemberStatus;
import parse.squarerefri.domain.member.domain.RoleType;
import parse.squarerefri.domain.member.exception.MemberNotEmailAuthException;
import parse.squarerefri.domain.member.exception.MemberStopUserException;
import parse.squarerefri.domain.member.model.MemberInput;
import parse.squarerefri.domain.member.model.ResetPasswordInput;
import parse.squarerefri.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Transactional
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            // userId가 존재하는 경우
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.createMember(parameter, encPassword, uuid);

        memberRepository.save(member);

        mailComponents.sendMail(MailTemplateType.REGISTER_CHECK, parameter.getUserId(),
                parameter.getUserName(), uuid);

        return true;
    }

    @Transactional
    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if(member.isEmailAuthFlag()) {
            return false;
        }

        member.updateEmailAuth();

        return true;
    }

    @Transactional
    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {

        Member member = memberRepository.findByIdAndMemberName(parameter.getUserId(), parameter.getUserName()).orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        String uuid = UUID.randomUUID().toString();

        member.updatePreResetPassword(uuid);

        mailComponents.sendMail(MailTemplateType.PASSWORD_RESET, parameter.getUserId(),
                parameter.getUserName(), uuid);

        return true;
    }

    @Transactional
    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        //초기화 날짜 유효 체크
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException(" 유효한 날짜가 아닙니다. ");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw  new RuntimeException(" 유효한 날짜가 아닙니다. ");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        //초기화 날짜 유효 체크
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException(" 유효한 날짜가 아닙니다. ");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw  new RuntimeException(" 유효한 날짜가 아닙니다. ");
        }
        return true;
    }

    @Transactional
    @Override
    public boolean updateStatus(String userId, String userStatus) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        member.setMemberStatus(MemberStatus.valueOf(userStatus));

        return true;
    }

    @Transactional
    @Override
    public boolean updatePassword(String userId, String password) {

        Member member = memberRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));


        if (MemberStatus.REQ.equals(member.getMemberStatus())) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인 해주세요");
        }

        if (MemberStatus.STOP.equals(member.getMemberStatus())) {
            throw new MemberStopUserException("정지된 회원입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (RoleType.ADMIN.equals(member.getRoleType())) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getId(), member.getPassword(), grantedAuthorities);
    }

    @Override
    public Member searchMember(String userId) {
       Member member = memberRepository.findById(userId)
               .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));
        return member;
    }
}
