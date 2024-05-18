package csec.accountbook.controller;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.service.ExpenseItemService;
import csec.accountbook.service.IncomeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String showExpenseList(Model model, @AuthenticationPrincipal UserDetails userDetails){

        List<ExpenseItem> expenseItems = expenseItemService.getExpenseItemsByMemberId(userDetails);
        model.addAttribute("expenseItems", expenseItems);

        int totalAmount = expenseItemService.getTotalExpensesAmount(expenseItems);
        model.addAttribute("totalAmount", totalAmount);

        return "expenseList";
    }

    @GetMapping("/incomeItemList")
    public String showIncomeList(Model model, @AuthenticationPrincipal UserDetails userDetails){
        List<IncomeItem> incomeItems = incomeItemService.getExpenseItemsByMemberId(userDetails);
        model.addAttribute("incomeItems", incomeItems);

        int totalIncomeAmount = incomeItemService.getTotalIncomeAmount(incomeItems);
        model.addAttribute("totalIncomeAmount", totalIncomeAmount);
        return "incomeList";
    }

    @GetMapping("/accountBookMenu")
    public String showAccountBookMenu(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }

}
