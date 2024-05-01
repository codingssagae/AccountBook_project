package csec.accountbook.controller;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.service.ExpenseItemService;
import csec.accountbook.service.IncomeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountBookController {

    private final ExpenseItemService expenseItemService;
    private final IncomeItemService incomeItemService;

    @GetMapping("/expenseItemList")
    public String showExpenseList(Model model){

        List<ExpenseItem> expenseItems = expenseItemService.getAllItems();
        model.addAttribute("expenseItems", expenseItems);

        int totalAmount = expenseItemService.getTotalExpensesAmount();
        model.addAttribute("totalAmount", totalAmount);

        return "expenseList";
    }

    @GetMapping("/incomeItemList")
    public String showIncomeList(Model model){
        List<IncomeItem> incomeItems = incomeItemService.getAllItems();
        model.addAttribute("incomeItems", incomeItems);

        int totalIncomeAmount = incomeItemService.getTotalIncomeAmount();
        model.addAttribute("totalIncomeAmount", totalIncomeAmount);
        return "incomeList";
    }

}
