package csec.accountbook.domain.repository;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.Income;
import csec.accountbook.domain.IncomeItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncomeRepository {

    private final EntityManager em;

    public void save(Income income){
        em.persist(income);
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
}
