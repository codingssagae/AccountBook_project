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
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
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
    private final S3ImageService s3ImageService;
    @Transactional
    public ExpenseItem save(String name, int price, int count, ItemType itemType2, String username, LocalDate localDate, MultipartFile imageFile) throws IOException {

        Optional<Member> member = memberRepository.findByUsername(username);
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setItemName(name);
        expenseItem.setSingleItemPrice(price);
        expenseItem.setItemCount(count);
        expenseItem.setMember(member.get());
        expenseItem.setPurchaseDate(localDate);

        for(ItemType itemType : ItemType.values()){
            if(itemType.name().equalsIgnoreCase(itemType2.toString())){
                expenseItem.setItemType(itemType);
                break;
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3ImageService.upload(imageFile);
            expenseItem.setImagePath(imageUrl);
        }

        expenseItem.setTotalItemPrice(count * price);
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

    public List<ExpenseItem> getItemsByTypeAndMemberId(UserDetails userDetails, ItemType itemType){
        if (itemType == null){
            return expenseItemRepository.findAll();
        }else{
            Optional<Long> id = memberRepository.findIdByUsername(userDetails.getUsername());
            return expenseItemRepository.findByMemberIdAndItemType(id.get(),itemType);
        }
    }

    public List<ExpenseItem> searchExpenseItems(UserDetails userDetails, String keyword){
        Optional<Long> id = memberRepository.findIdByUsername(userDetails.getUsername());
        return expenseItemRepository.findByMemberIdAndItemNameContaining(id.get(),keyword);
    }

    public List<ExpenseItem> searchExpenseItemsByType(UserDetails userDetails, ItemType itemType, String keyword){
        Optional<Long> id = memberRepository.findIdByUsername(userDetails.getUsername());
        if (itemType == null){
            return searchExpenseItems(userDetails, keyword);
        }
        else {
            return expenseItemRepository.findByMemberIdAndItemTypeAndItemNameContaining(id.get(),itemType,keyword);
        }
    }

}
