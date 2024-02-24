package csec.accountbook.domain.repository;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
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

    @Transactional
    public void updateFoodExpenseAmount(Expense expense){

        List<ExpenseItem> foodItemList = em.createQuery("select i from ExpenseItem i where i.itemType =: type", ExpenseItem.class)
                .setParameter("type", ItemType.FOOD)
                .getResultList();
        int amount = 0;
        for(ExpenseItem item : foodItemList){
            amount+=item.getItemPrice();
        }

        expense.setFoodExpenseAmount(amount);
        em.merge(expense);
    }

    @Transactional
    public void updateClothesExpenseAmount(Expense expense){

        List<ExpenseItem> clothesItemList = em.createQuery("select i from ExpenseItem i where i.itemType =: type", ExpenseItem.class)
                .setParameter("type", ItemType.CLOTHES)
                .getResultList();
        int amount = 0;
        for(ExpenseItem item : clothesItemList){
            amount+=item.getItemPrice();
        }

        expense.setClothesExpenseAmount(amount);
        em.merge(expense);
    }

}
