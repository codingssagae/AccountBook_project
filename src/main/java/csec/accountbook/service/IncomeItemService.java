package csec.accountbook.service;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.repository.IncomeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeItemService {

    private final IncomeItemRepository incomeItemRepository;
    @Transactional
    public IncomeItem save(int amount, String path){
        IncomeItem item = new IncomeItem();
        item.setIncomeAmount(amount);
        item.setIncomePath(path);
        incomeItemRepository.save(item);
        return item;
    }


    public List<IncomeItem> getAllItems(){
        return incomeItemRepository.findAll();
    }

    public int getTotalIncomeAmount() {
        List<IncomeItem> incomeItems = getAllItems();
        int totalAmount = 0;
        for(IncomeItem item : incomeItems){
            totalAmount+=item.getIncomeAmount();
        }
        return totalAmount;
    }
}
