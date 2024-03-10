package csec.accountbook.service;

import csec.accountbook.domain.*;
import csec.accountbook.repository.IncomeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final EntityManager em;
    @Transactional
    public Income createIncome(Member member){
        Income income = new Income();
        income.setMember(member);
        incomeRepository.save(income);
        return income;
    }
    @Transactional
    public void saveItem(Income income, IncomeItem ... items){
        for (IncomeItem item : items){
            item.setIncome(income);
            income.getIncomeItems().add(item);
        }
    }



    @Transactional
    public void updateTotalIncomeAmount(Income income){

        List<IncomeItem> incomeItemList = em.createQuery("select i from IncomeItem i where i.income = :income", IncomeItem.class)
                .setParameter("income", income)
                .getResultList();

        int totalAmount = 0;
        for(IncomeItem item : incomeItemList){
            totalAmount+=item.getIncomeAmount();
        }
        income.setTotalIncomeAmount(totalAmount);
        em.merge(income);
    }


    public int getTotalIncomeAmount(){
        Optional<Income> income = incomeRepository.findById(1L);
        Income income1 = income.get();
        int totalIncomeAmount = income1.getTotalIncomeAmount();
        return totalIncomeAmount;
    }


}
