package csec.accountbook.controller;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.MemberRepository;
import csec.accountbook.service.ExpenseItemService;
import csec.accountbook.service.IncomeItemService;
import csec.accountbook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AccountBookController {

    private final ExpenseItemService expenseItemService;
    private final IncomeItemService incomeItemService;
    private final MemberRepository memberRepository;

    @GetMapping("/expenseItemList")
    public String showExpenseList(Model model, @AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam(required = false) ItemType itemType,
                                  @RequestParam(required = false) String keyword) {

        List<ExpenseItem> expenseItems;
        if (keyword != null && !keyword.isEmpty()) {
            expenseItems = expenseItemService.searchExpenseItemsByType(userDetails,itemType ,keyword);
        } else {
            expenseItems = expenseItemService.getItemsByTypeAndMemberId(userDetails, itemType);
        }
        model.addAttribute("expenseItems", expenseItems);

        int totalAmount = expenseItemService.getTotalExpensesAmount(expenseItems);
        model.addAttribute("totalAmount", totalAmount);

        return "expenseList";
    }

    @GetMapping("/incomeItemList")
    public String showIncomeList(Model model, @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(required = false) String keyword){
        List<IncomeItem> incomeItems;
        if (keyword !=null && !keyword.isEmpty()){
            incomeItems = incomeItemService.searchIncomeItems(userDetails,keyword);
        }
        else{
            incomeItems = incomeItemService.getExpenseItemsByMemberId(userDetails);
        }
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

    @GetMapping("/findFriendPage")
    public String findFriendPage(){
        return "findFriend";
    }

    @GetMapping("/findFriend")
    public String findFriend(@RequestParam("email") String email, Model model) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            model.addAttribute("friend", null);
        }else{
            model.addAttribute("friend", member.get());
        }
        return "findFriend";
    }


}
