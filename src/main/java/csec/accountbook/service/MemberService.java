package csec.accountbook.service;
import csec.accountbook.domain.Follow;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.FollowRepository;
import csec.accountbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;

    public Member createMember(String username, String email, String password){
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        return member;
    }

    public Long getUserIdByUsername(String name){
        return memberRepository.findIdByUsername(name)
                .orElseThrow(()->new UsernameNotFoundException("Not found"));
    }

    public List<Member> getFollowingList(Long memberId){
        List<Follow> follows = followRepository.findAllByFromUser(memberId);
        List<Long> toUserIds = follows.stream()
                .map(Follow::getToUser)
                .collect(Collectors.toList());
        return memberRepository.findAllById(toUserIds);
    }

    public List<Member> getFollowersList(Long memberId) {
        List<Follow> follows = followRepository.findAllByToUser(memberId);
        List<Long> fromUserIds = follows.stream()
                .map(Follow::getFromUser)
                .collect(Collectors.toList());
        return memberRepository.findAllById(fromUserIds);
    }

}
