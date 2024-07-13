package csec.accountbook.api.dto;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.IncomeItem;
import csec.accountbook.domain.Member;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberDetailsDTO {

    private Long memberId;
    private String username;
    private List<ExpenseItemDto> expenseItems;
    private List<IncomeItemDto> incomeItems;

    public MemberDetailsDTO(Member member){
        memberId = member.getId();
        username = member.getUsername();
        expenseItems = member.getExpenseItems().stream()
                .map(expenseItem -> new ExpenseItemDto(expenseItem))
                .collect(Collectors.toList());
        incomeItems = member.getIncomeItems().stream()
                .map(incomeItem -> new IncomeItemDto(incomeItem))
                .collect(Collectors.toList());
    }
}
