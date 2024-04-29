package csec.accountbook.service;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.ExpenseItemRepository;
import csec.accountbook.repository.ExpenseRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseItemRepository expenseItemRepository;
    private final EntityManager em;
    @Transactional
    public Expense createExpense(Member member){
        Expense expense = new Expense();
        expense.setMember(member);
        expenseRepository.save(expense);
        return expense;
    }

    @Transactional
    public void saveItem(Expense expense, ExpenseItem ... items){
        for (ExpenseItem item : items) {
            item.setExpense(expense);
            expense.getExpenseItems().add(item);
        }
    }
    public List<ExpenseItem> getAllExpenseItems() {
        return expenseItemRepository.findAll();
    }

    public int getTotalAmount(){
        List<ExpenseItem> allExpenseItems = getAllExpenseItems();
        int tmp = 0;
        for(ExpenseItem expenseItem : allExpenseItems){
            tmp+=expenseItem.getTotalItemPrice();
        }
        return tmp;
    }

    @Transactional
    public void updateTotalExpenseAmount(Expense expense){

        List<ExpenseItem> expenseItems = em.createQuery("select i from ExpenseItem i where i.expense = :expense", ExpenseItem.class)
                .setParameter("expense", expense)
                .getResultList();

        int totalExpenseAmount = 0;
        for(ExpenseItem item : expenseItems){
            totalExpenseAmount+=item.getTotalItemPrice();
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
            amount+=item.getTotalItemPrice();
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
            amount+=item.getTotalItemPrice();
        }

        expense.setClothesExpenseAmount(amount);
        em.merge(expense);
    }


}
