package csec.accountbook.service;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.IncomeItemRepository;
import csec.accountbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.IntConsumer;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeItemService {

    private final IncomeItemRepository incomeItemRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public IncomeItem save(int amount, String path, String username, LocalDate localDate){
        Optional<Member> member = memberRepository.findByUsername(username);
        IncomeItem item = new IncomeItem();
        item.setIncomeAmount(amount);
        item.setIncomePath(path);
        item.setMember(member.get());
        item.setIncomeDate(localDate);
        incomeItemRepository.save(item);
        return item;
    }


    public List<IncomeItem> getExpenseItemsByMemberId(UserDetails userDetails){
        Optional<Long> id = memberRepository.findIdByUsername(userDetails.getUsername());
        return incomeItemRepository.findByMemberId(id.get());
    }

    public int getTotalIncomeAmount(List<IncomeItem> incomeItems) {
        int totalAmount = 0;
        for(IncomeItem item : incomeItems){
            totalAmount+=item.getIncomeAmount();
        }
        return totalAmount;
    }
}
