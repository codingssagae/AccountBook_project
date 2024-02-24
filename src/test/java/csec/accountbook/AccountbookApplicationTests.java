package csec.accountbook;

import csec.accountbook.domain.*;
import csec.accountbook.domain.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AccountbookApplicationTests {

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    ExpenseItemRepository expenseItemRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    IncomeItemRepository incomeItemRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void test11(){

        //Given

        Member member = new Member();
        member.setAge(25);
        member.setName("Lee");

        Expense expense = new Expense();
        expense.setMember(member);

        Income income = new Income();
        income.setMember(member);

        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setItemName("Noodle");
        expenseItem.setItemCount(1);
        expenseItem.setItemPrice(10000);
        expenseItem.setItemType(ItemType.FOOD);
        expenseItem.setExpense(expense);
        ExpenseItem expenseItem2 = new ExpenseItem();
        expenseItem2.setItemName("노스페이스 패딩");
        expenseItem2.setItemCount(1);
        expenseItem2.setItemPrice(250000);
        expenseItem2.setItemType(ItemType.CLOTHES);
        expenseItem2.setExpense(expense);
        ExpenseItem expenseItem3 = new ExpenseItem();
        expenseItem3.setItemName("톰브라운 가디건");
        expenseItem3.setItemCount(1);
        expenseItem3.setItemPrice(1700000);
        expenseItem3.setItemType(ItemType.CLOTHES);
        expenseItem3.setExpense(expense);

        IncomeItem incomeItem = new IncomeItem();
        incomeItem.setIncomeAmount(350000);
        incomeItem.setIncomePath("월급받음");
        incomeItem.setIncome(income);
        IncomeItem incomeItem2 = new IncomeItem();
        incomeItem2.setIncomeAmount(100000);
        incomeItem2.setIncomePath("새뱃돈 겟");
        incomeItem2.setIncome(income);

        member.setIncome(income);
        member.setExpense(expense);

        memberRepository.save(member);
        expenseRepository.save(expense);
        expenseItemRepository.save(expenseItem);
        expenseItemRepository.save(expenseItem2);
        expenseItemRepository.save(expenseItem3);
        incomeItemRepository.save(incomeItem);
        incomeItemRepository.save(incomeItem2);
        incomeRepository.save(income);


        em.flush();
        em.clear();

        expenseRepository.updateTotalExpenseAmount(expense);
        expenseRepository.updateClothesExpenseAmount(expense);
        expenseRepository.updateFoodExpenseAmount(expense);
        incomeRepository.updateTotalIncomeAmount(income);


    }

}
