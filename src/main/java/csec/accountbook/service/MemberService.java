package csec.accountbook.service;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.Income;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public Member createMember(String name, int age){

        Member member = new Member();
        member.setName(name);
        member.setAge(age);

        memberRepository.save(member);
        return member;
    }

}
