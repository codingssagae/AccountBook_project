package csec.accountbook;

import csec.accountbook.domain.*;
import csec.accountbook.service.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AccountbookApplicationTests {


    @Autowired EntityManager em;
    @Autowired ExpenseItemService expenseItemService;
    @Autowired
    ExpenseService expenseSevice;
    @Autowired MemberService memberService;
    @Autowired
    IncomeService incomeService;
    @Autowired
    IncomeItemService incomeItemService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void test11(){

        //given

        ExpenseItem item1 = expenseItemService.save("막창", 55000, 1, "food");
        ExpenseItem item2 = expenseItemService.save("가디건", 27000, 3, "clothes");
        ExpenseItem item5 = expenseItemService.save("야끼우동", 12500, 1, "food");
        IncomeItem item3 = incomeItemService.save(560000, "설날 뽀찌");
        IncomeItem item4 = incomeItemService.save(4900000, "월급");

        Member member = memberService.createMember("이건희", 25);

        Expense expense = expenseSevice.createExpense(member);
        Income income = incomeService.createIncome(member);

        //when

        expenseSevice.saveItem(expense,item1,item2,item5);
        expenseSevice.updateClothesExpenseAmount(expense);
        expenseSevice.updateTotalExpenseAmount(expense);
        expenseSevice.updateFoodExpenseAmount(expense);

        incomeService.saveItem(income,item3,item4);
        incomeService.updateTotalIncomeAmount(income);

        em.flush();
        em.clear();


    }

}
