package csec.accountbook.api.dto;

import csec.accountbook.domain.Member;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberExpenseItemsDto {

    private Long memberId;
    private List<ExpenseItemDto> expenseItems;

    public MemberExpenseItemsDto(Member member) {
        memberId = member.getId();
        expenseItems = member.getExpenseItems().stream()
                .map(expenseItem -> new ExpenseItemDto(expenseItem))
                .collect(Collectors.toList());
    }
}
