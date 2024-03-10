package csec.accountbook.service;


import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.repository.ExpenseItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExpenseItemService  {

    private final ExpenseItemRepository expenseItemRepository;
    @Transactional
    public ExpenseItem save(String name, int price, int count, String type){

        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setItemName(name);
        expenseItem.setSingleItemPrice(price);;
        expenseItem.setItemCount(count);
        for(ItemType itemType : ItemType.values()){
            if(itemType.name().equalsIgnoreCase(type)){
                expenseItem.setItemType(itemType);
                break;
            }
        }
        expenseItem.setTotalItemPrice(count*price);
        expenseItemRepository.save(expenseItem);
        return expenseItem;
    }

    public List<ExpenseItem> getAllItems(){
        return expenseItemRepository.findAll();
    }



}
