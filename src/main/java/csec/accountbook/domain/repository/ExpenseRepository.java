package csec.accountbook.domain.repository;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.ExpenseItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpenseRepository {

    private final EntityManager em;

    public void save(Expense expense){
        em.persist(expense);
    }

    @Transactional
    public void updateTotalExpenseAmount(Expense expense){

        List<ExpenseItem> expenseItems = em.createQuery("select i from ExpenseItem i where i.expense = :expense", ExpenseItem.class)
                .setParameter("expense", expense)
                .getResultList();

        int totalExpenseAmount = 0;
        for(ExpenseItem item : expenseItems){
            totalExpenseAmount+=item.getItemPrice();
        }

        expense.setTotalExpenseAmount(totalExpenseAmount);
        em.merge(expense);
    }
}
