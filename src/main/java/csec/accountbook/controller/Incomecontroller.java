package csec.accountbook.controller;


import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.Income;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.service.IncomeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class Incomecontroller {

    private final IncomeItemService incomeItemService;

    @GetMapping("/incomeItemForm")
    public String showIncomeForm(Model model){
        model.addAttribute("incomeItem", new IncomeItem());
        return "incomeItemForm";
    }

    @PostMapping("/income/save")
    public String saveIncome(@ModelAttribute IncomeItem incomeItem, BindingResult result){
        if (result.hasErrors()){
            return "incomeItemForm";
        }
        incomeItemService.save(incomeItem.getIncomeAmount(),incomeItem.getIncomePath());
        return "redirect:/";
    }


}
