package csec.accountbook.service;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(String username, String email, String password){
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        return member;
    }
}
