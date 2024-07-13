package csec.accountbook.api;

import csec.accountbook.api.dto.MemberDetailsDTO;
import csec.accountbook.api.dto.MemberExpenseItemsDto;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    @GetMapping("/api/v2/memberdetails/{id}")
    public MemberDetailsDTO memberApiV2(@PathVariable Long id){
        Optional<Member> member = memberRepository.findById(id);
         return new MemberDetailsDTO(member.get());
    }
    @GetMapping("/api/v3/memberdetails/{id}")
    public MemberDetailsDTO memberApiV3(@PathVariable Long id){
        Member member = findMemberDetailsQuery(id);
        return new MemberDetailsDTO(member);
    }

    @GetMapping("api/memberExpenseItemsApi/{id}")
    public MemberExpenseItemsDto memberExpenseItemsApi(@PathVariable Long id){
        Member member = findExpenseItemOfMember(id);
        return new MemberExpenseItemsDto(member);
    }

    private Member findExpenseItemOfMember(Long memberId){
        TypedQuery<Member> query = em.createQuery(
                "select m from Member m" +
                        " join fetch m.expenseItems ei" +
                        " where m.id = :memberId", Member.class
        );
        query.setParameter("memberId", memberId);
        return query.getSingleResult();
    }

    private Member findMemberDetailsQuery(Long memberId){
        TypedQuery<Member> query = em.createQuery(
                "select m from Member m" +
                        " join fetch m.expenseItems ei" +
                        " join fetch m.incomeItems ii" +
                        " where m.id = :memberId", Member.class
        );
        query.setParameter("memberId", memberId);
        return query.getSingleResult();
    }

}
