package csec.accountbook.controller;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.service.ExpenseItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseItemService expenseItemService;

    @GetMapping("/expenseItemForm")
    public String showExpenseForm(Model model){
        model.addAttribute("expenseItem", new ExpenseItem());
        model.addAttribute("itemTypes", ItemType.values());
        return "expenseItemForm";
    }

    @PostMapping("/expenses/save")
    public String saveExpense(@ModelAttribute ExpenseItem expenseItem, BindingResult result){
        if (result.hasErrors()){
            return "expenseItemForm";
        }
        expenseItemService.save(expenseItem.getItemName(), expenseItem.getSingleItemPrice(),
                expenseItem.getItemCount(), expenseItem.getItemType());
        return "redirect:/";
    }

}
