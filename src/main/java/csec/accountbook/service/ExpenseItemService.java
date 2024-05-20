package csec.accountbook.service;


import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.ExpenseItemRepository;
import csec.accountbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExpenseItemService  {

    private final ExpenseItemRepository expenseItemRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @Transactional
    public ExpenseItem save(String name, int price, int count, ItemType itemType2, String username, LocalDate localDate){

        Optional<Member> member = memberRepository.findByUsername(username);
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setItemName(name);
        expenseItem.setSingleItemPrice(price);;
        expenseItem.setItemCount(count);
        expenseItem.setMember(member.get());
        expenseItem.setPurchaseDate(localDate);
        for(ItemType itemType : ItemType.values()){
            if(itemType.name().equalsIgnoreCase(itemType2.toString())){
                expenseItem.setItemType(itemType);
                break;
            }
        }
        expenseItem.setTotalItemPrice(count*price);
        expenseItemRepository.save(expenseItem);
        return expenseItem;
    }



    public int getTotalExpensesAmount(List<ExpenseItem> expenseItems) {
        int totalAmount = 0;
        for(ExpenseItem item : expenseItems){
            totalAmount+=item.getTotalItemPrice();
        }
        return totalAmount;
    }

    public List<ExpenseItem> getExpenseItemsByMemberId(UserDetails userDetails){
        Optional<Long> id = memberRepository.findIdByUsername(userDetails.getUsername());
        return expenseItemRepository.findByMemberId(id.get());
    }
}
