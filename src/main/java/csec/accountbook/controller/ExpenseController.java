package csec.accountbook.controller;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.service.ExpenseItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseItemService expenseItemService;

    @GetMapping("/expenseItemForm")
    public String showExpenseForm(Model model) {
        model.addAttribute("expenseItem", new ExpenseItem());
        model.addAttribute("itemTypes", ItemType.values());
        return "expenseItemForm";
    }

    @PostMapping("/expenses/save")
    public String saveExpense(@ModelAttribute ExpenseItem expenseItem, BindingResult result,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "expenseItemForm";
        }

        try {
            expenseItemService.save(expenseItem.getItemName(), expenseItem.getSingleItemPrice(),
                    expenseItem.getItemCount(), expenseItem.getItemType(), userDetails.getUsername(),
                    expenseItem.getPurchaseDate(), imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "expenseItemForm";
        }

        return "redirect:/accountBookMenu";
    }
}
